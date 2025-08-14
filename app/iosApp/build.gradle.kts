// app/androidApp/build.gradle.kts
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.moko.resources) // 应用 MOKO 插件:cite[2]:cite[3]
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        iosMain.dependencies {
            // 功能模块
            implementation(project(":features:feature-auth"))
            implementation(project(":features:feature-home"))

            // Compose
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)

            // MOKO
            implementation(libs.moko.resources)
            implementation(libs.moko.resources.compose)
        }
    }
}

compose.experimental {
//    uikit.application {
//        projectName = "YourProject"
//        bundleIdPrefix = "com.yourproject"
//        deployConfigurations {
//            simulator("IPhone13Pro") {
//                device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_13_PRO
//            }
//            connectedDevice("Device") {
//                this.teamId = "YOUR_TEAM_ID"
//            }
//        }
//    }
}