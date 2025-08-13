package com.ax.tototoproj.network

import io.ktor.client.HttpClient

import io.ktor.client.engine.okhttp.*
import okhttp3.Protocol


actual fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
    configureCommon()
    // Android 特定配置
    engine {
        config {
            // 自定义 OkHttp 配置
            followRedirects(true)
            // 启用HTTP2支持
            protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
        }
    }
}