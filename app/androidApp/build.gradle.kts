// app/androidApp/build.gradle.kts
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.moko.resources) // 应用 MOKO 插件:cite[2]:cite[3]
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

android {

    namespace = "com.ax.tototoproj"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileSdkVersion = "android-35"
//    sourceSets["main"].res.srcDirs(
//        "src/androidMain/res",
//        "${buildDir}/generated/moko/androidMain/res"
//    )

    defaultConfig {
        applicationId = "com.ax.tototoproj"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true  // 添加此行启用 multidex
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // 功能模块
    implementation(project(":features:feature-auth"))
    implementation(project(":features:feature-home"))

    implementation(compose.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.ktor.client.okhttp)
}