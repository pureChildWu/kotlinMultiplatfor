package com.ax.tototoproj

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.trees.composeui.components.ToastHost
import io.kamel.core.config.DefaultCacheSize
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.fileFetcher
import io.kamel.core.config.httpUrlFetcher
import io.kamel.image.config.imageBitmapDecoder
import io.kamel.image.config.imageVectorDecoder
import io.kamel.image.config.svgDecoder
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.isSuccess

@Composable
fun App() {
    // 设置全局Kamel配置
    initKamel()
    // 使用 Navigator 管理主页面
    Navigator(LoginScreen())

    // 添加Toast宿主到根布局
//    ToastHost()
}

fun initKamel() {
    val customKamelConfig = KamelConfig {
        // Copies the default implementation if needed
//        takeFrom(KamelConfig.Default)
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
//            defaultRequest {
//                url("https://www.example.com/")
//                cacheControl(CacheControl.MaxAge(maxAgeSeconds = 10000))
//            }

            // 安装 HttpRequestRetry 插件
            install(HttpRequestRetry) {
                maxRetries = 3
                retryIf { httpRequest, httpResponse ->
                    !httpResponse.status.isSuccess()
                }
            }

            // 移除重复的 HttpTimeout 配置
            // 超时设置已在 HttpClient.kt 中统一配置

            // Requires adding "io.ktor:ktor-client-logging:$ktor_version"
            Logging {
                level = LogLevel.INFO
                logger = Logger.SIMPLE
            }
        }

        // more functionality available.
    }

}