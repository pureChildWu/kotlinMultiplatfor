package com.ax.tototoproj.network

import io.ktor.client.HttpClient
// shared/src/iosMain/kotlin/network/HttpClientFactory.kt
import io.ktor.client.engine.darwin.*
import platform.Foundation.NSURLRequestCachePolicy

actual fun createHttpClient(): HttpClient = HttpClient(Darwin) {
    configureCommon()

    // iOS 特定配置
    engine {
        configureRequest {
            // 自定义 NSURLSession 配置
            setAllowsCellularAccess(true)
            setTimeoutInterval(30.0)
            // 设置缓存策略为最大值，允许最大程度地利用缓存数据
            setCachePolicy(NSURLRequestCachePolicy.MAX_VALUE)
        }
    }
}