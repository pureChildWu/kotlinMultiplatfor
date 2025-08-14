rootProject.name = "ToToToProj"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

//include(":composeApp")
// 包含构建逻辑
include(":build-logic:conventions")

// 包含所有模块
include(":core")
include(":data")
include(":domain")
include(":ui")
include(":features:feature-auth")
include(":features:feature-home")
include(":app:androidApp")
include(":app:iosApp")