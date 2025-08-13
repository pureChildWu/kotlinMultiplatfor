package com.ax.tototoproj.network

import io.ktor.client.HttpClient

import io.ktor.client.engine.okhttp.*


actual fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
    configureCommon()
    // Android 特定配置
    engine {
        config {
            // 自定义 OkHttp 配置
            followRedirects(true)
        }
    }
}