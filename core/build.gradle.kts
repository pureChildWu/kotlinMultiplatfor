
plugins {
    alias(libs.plugins.kotlinMultiplatform)
//    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.moko.resources) // 应用 MOKO 插件:cite[2]:cite[3]
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kotlin {
//    androidTarget {
////        compilerOptions {
////            jvmTarget.set(JvmTarget.JVM_11)
////        }
//    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            // 关键：确保资源打包到 iOS 框架
            binaryOptions["bundleResources"] = "true"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
//            implementation(libs.accompanist.permissions)
//            implementation(libs.camera.core)
//            implementation(libs.camera.camera2)
//            implementation(libs.camera.lifecycle)
//            implementation(libs.camera.view)
//            implementation(libs.image.cropper)
            // Android 特定的 Kamel 组件
//            implementation(libs.kamel.decoder.image.bitmap.resizing)
//            implementation(libs.kamel.fetcher.resources.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.voyager.bottomSheetNavigator)
            // 添加 MOKO Resources 依赖
            implementation(libs.moko.resources)
            implementation(libs.moko.resources.compose)
            // 添加Kamel图片加载库
            implementation(libs.kamel.image.default)

            // Ktor 客户端
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.cio)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

// 关键配置：指定资源包名和位置
multiplatformResources {
    resourcesPackage = "com.ax.tototoproj" // 必须与你的包名一致
    resourcesClassName = "MR" // 生成的类名
//    resourcesVisibility.set(MRVisibility.Internal) // optional, default Public
    iosBaseLocalizationRegion.set("en") // optional, default "en"
    iosMinimalDeploymentTarget.set("11.0") // optional, default "9.0"
    resourcesSourceSets {
        getByName("commonMain").srcDirs(  // 将jvmMain改为androidMain
//            File(projectDir,"src/commonMain/resources")
            "src/commonMain/resources"  // 修改为这个路径
        )
    }
}

//android {
////    compileSdk = libs.versions.android.compileSdk.get().toInt()
//
//    sourceSets["main"].res.srcDirs(
//        "src/androidMain/res",
//        "${buildDir}/generated/moko/androidMain/res"
//    )
//
//    defaultConfig {
//        applicationId = "com.ax.tototoproj"
//        minSdk = libs.versions.android.minSdk.get().toInt()
//        targetSdk = libs.versions.android.targetSdk.get().toInt()
//        versionCode = 1
//        versionName = "1.0"
//        multiDexEnabled = true  // 添加此行启用 multidex
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
//    buildTypes {
//        getByName("release") {
//            isMinifyEnabled = false
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//}
//
//dependencies {
//    debugImplementation(compose.uiTooling)
//}