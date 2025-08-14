// 根项目 build.gradle.kts

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
//
//    alias(libs.plugins.kotlinSerialization) apply false
////    alias(libs.plugins.sqldelight) apply false
////    alias(libs.plugins.koin) apply false
}


// 配置所有子项目
subprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    // 通用配置
    afterEvaluate {
        // 所有模块添加基础Kotlin配置
        if (plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
//            extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
//                sourceSets {
//                    all {
//                        languageSettings {
//                            optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
//                            optIn("androidx.compose.material3.ExperimentalMaterial3Api")
//                        }
//                    }
//                }
//            }
        }

        // 添加Java 17兼容性
//        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
//            kotlinOptions {
//                jvmTarget = "17"
//            }
//        }
    }
}

// 配置构建缓存
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}