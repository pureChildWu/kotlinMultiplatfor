https://github.com/Kamel-Media/Kamel

Setup
Kamel is published on Maven Central:

repositories {
mavenCentral()
// ...
}
Default Setup
Add the dependency to the common source-set or to any platform source-set:

kotlin {
sourceSets {
commonMain {
dependencies {
implementation("media.kamel:kamel-image-default:1.0.7")
// no need to specify ktor engines, one is included for each target
// ...
}
}
}
}
Granular Setup
For a more granular setup, you can choose which modules to include in your project:

kotlin {
sourceSets {
commonMain {
dependencies {
// core module (required)
implementation("media.kamel:kamel-image:1.0.7")

                // Note: When using `kamel-image` a ktor engine is not included.
                // To fetch remote images you also must ensure you add your own 
                // ktor engine for each target.
                
                // optional modules (choose what you need and add them to your kamel config)
                implementation("media.kamel:kamel-decoder-image-bitmap:1.0.7")
                implementation("media.kamel:kamel-decoder-image-bitmap-resizing:1.0.7") // android only right now
                implementation("media.kamel:kamel-decoder-image-vector:1.0.7")
                implementation("media.kamel:kamel-decoder-svg-batik:1.0.7")
                implementation("media.kamel:kamel-decoder-svg-std:1.0.7")
                implementation("media.kamel:kamel-decoder-animated-image:1.0.7") // .gif support

                implementation("media.kamel:kamel-fetcher-resources-jvm:1.0.7")
                implementation("media.kamel:kamel-fetcher-resources-android:1.0.7")
                // ...
            }
        }

        jvmMain {
            dependencies {
                // optional modules (choose what you need and add them to your kamel config)
                implementation("media.kamel:kamel-fetcher-resources-jvm:1.0.7")
                // ...
            }
        }

        androidMain {
            dependencies {
                // optional modules (choose what you need and add them to your kamel config)
                implementation("media.kamel:kamel-fetcher-resources-android:1.0.7")
                // ...
            }
        }
    }
}
Granular Setup: Ktor HttpClient Engine
When using kamel-image ktor engines are not included per target. In order to fetch remote images you also must ensure you add your own ktor engine for each target. You can find the available engines here.

Usage
Loading an image resource
To load an image asynchronously, you can use asyncPainterResource composable, it can load images from different data sources:

// String
asyncPainterResource(data = "https://www.example.com/image.jpg")
asyncPainterResource(data = "file:///path/to/image.png")

// Ktor Url
asyncPainterResource(data = Url("https://www.example.com/image.jpg"))

// URI
asyncPainterResource(data = URI("https://www.example.com/image.png"))

// File (JVM, Native)
asyncPainterResource(data = File("/path/to/image.png"))

// File (JS)
asyncPainterResource(data = File(org.w3c.files.File(arrayOf(blob), "/path/to/image.png")))

// URL
asyncPainterResource(data = URL("https://www.example.com/image.jpg"))

// and more...
asyncPainterResource can be used to load SVG, XML, JPEG, and PNG by default depending on the platform implementation.

asyncPainterResource returns a Resource<Painter> object which can be used to display the image using KamelImage composable.

Platform specific implementations
Since there isn't any shared resource system between Android and Desktop, some implementations (e.g. fetchers and mappers) are only available for a specific platform:

Desktop only implementations
To load an image file from desktop application resources, you have to add resourcesFetcher to the KamelConfig:

val desktopConfig = KamelConfig {
takeFrom(KamelConfig.Default)
// Available only on Desktop.
resourcesFetcher()
// Available only on Desktop.
// An alternative svg decoder
batikSvgDecoder()
}
Assuming there's an image.png file in the /resources directory in the project:

CompositionLocalProvider(LocalKamelConfig provides desktopConfig) {
asyncPainterResource("image.png")
}
Android only implementations
To load an image file from android application resources, you have to add resourcesFetcher and resourcesIdMapper to the KamelConfig:

val context: Context = LocalContext.current

val androidConfig = KamelConfig {
takeFrom(KamelConfig.Default)
// Available only on Android.
resourcesFetcher(context)
// Available only on Android.
resourcesIdMapper(context)
}
Assuming there's an image.png file in the /res/raw directory in the project:

CompositionLocalProvider(LocalKamelConfig provides androidConfig) {
asyncPainterResource(R.raw.image)
}
Configuring an image resource
asyncPainterResource supports configuration using a trailing lambda:

val painterResource: Resource<Painter> = asyncPainterResource("https://www.example.com/image.jpg") {

    // CoroutineContext to be used while loading the image.
    coroutineContext = Job() + Dispatcher.IO

    // Customizes HTTP request
    requestBuilder { // this: HttpRequestBuilder
        header("Key", "Value")
        parameter("Key", "Value")
        cacheControl(CacheControl.MAX_AGE)
    }

}
Displaying an image resource
KamelImage is a composable function that takes a Resource<Painter> object, display it and provide extra functionality:

KamelImage(
resource = painterResource,
contentDescription = "Profile",
)
KamelImage can also be used to get the exception using onFailure, and progress using onLoading parameters, to display a snackbar or a progress indicator, depending on the case:

val coroutineScope = rememberCoroutineScope()
val snackbarHostState = remember { SnackbarHostState() }
Box {
KamelImage(
resource = painterResource,
contentDescription = "Profile",
onLoading = { progress -> CircularProgressIndicator(progress) },
onFailure = { exception ->
coroutineScope.launch {
snackbarHostState.showSnackbar(
message = exception.message.toString(),
actionLabel = "Hide",
duration = SnackbarDuration.Short
)
}
}
)
SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(16.dp))
}
You can also provide your own custom implementation using a simple when expression:

when (val resource = asyncPainterResource("https://www.example.com/image.jpg")) {
is Resource.Loading -> {
Text("Loading...")
}
is Resource.Success -> {
val painter: Painter = resource.value
Image(painter, contentDescription = "Profile")
}
is Resource.Failure -> {
log(resource.exception)
val fallbackPainter = painterResource("/path/to/fallbackImage.jpg")
Image(fallbackPainter, contentDescription = "Profile")
}
}
Crossfade animation
You can enable, disable or customize crossfade (fade-in) animation through the animationSpec parameter. Setting animationSpec to null will disable the animation:

KamelImage(
resource = imageResource,
contentDescription = "Profile",
// null by default
animationSpec = tween(),
)
Configuring Kamel
The default implementation is KamelConfig.Default. If you wish to configure it, you can do it the following way:

val customKamelConfig = KamelConfig {
// Copies the default implementation if needed
takeFrom(KamelConfig.Default)

    // Sets the number of images to cache
    imageBitmapCacheSize = DefaultCacheSize

    // adds an ImageBitmapDecoder
    imageBitmapDecoder()

    // adds an ImageVectorDecoder (XML)
    imageVectorDecoder()

    // adds an SvgDecoder (SVG)
    svgDecoder()

    // adds a FileFetcher
    fileFetcher()

    
    // Configures Ktor HttpClient
    httpUrlFetcher {
        // httpCache is defined in kamel-core and configures the ktor client 
        // to install a HttpCache feature with the implementation provided by Kamel.
        // The size of the cache can be defined in Bytes.
        httpCache(10 * 1024 * 1024  /* 10 MiB */)

        defaultRequest {
            url("https://www.example.com/")
            cacheControl(CacheControl.MaxAge(maxAgeSeconds = 10000))
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { httpRequest, httpResponse ->
                !httpResponse.status.isSuccess()
            }
        }

        // Requires adding "io.ktor:ktor-client-logging:$ktor_version"
        Logging {
            level = LogLevel.INFO
            logger = Logger.SIMPLE
        }
    }

    // more functionality available.
}
Memory cache size (number of entries to cache)
Kamel provides a generic Cache<K,V> interface, the default implementation uses LRU memory cache mechanism backed by LinkedHashMap. You can provide a number of entries to cache for each type like so:

KamelConfig {
// 100 by default
imageBitmapCacheSize = 500
// 100 by default
imageVectorCacheSize = 300
// 100 by default
svgCacheSize = 200
}
Disk cache size (in bytes)
Kamel can create a persistent disk cache for images by implementing ktor's CacheStorage feature. The default config KamelConfig.Default installs this feature with a 10 MiB disk cache size. The underlying disk cache is based on coil's multiplatform DiskLruCache implementation.

KamelConfig {
httpFetcher {
// The size of the cache can be defined in bytes. Or DefaultHttpCacheSize (10 MiB) can be used.
httpCache(DefaultHttpCacheSize)
}
}
Applying Kamel configuration
You can use LocalKamelConfig to apply your custom configuration:

CompositionLocalProvider(LocalKamelConfig provides customKamelConfig) {
asyncPainterResource("image.jpg")
}
Contributions
Contributions are always welcome!. If you'd like to contribute, please feel free to create a PR or open an issue.