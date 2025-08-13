package com.ax.tototoproj.network

/**
 * @author : lihuiwu
 * @date : 2025/8/12
 * @desc :
 */
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

// 通用配置
internal fun HttpClientConfig<*>.configureCommon() {
    install(ContentNegotiation) {
        json(json)
    }
    install(Logging) {
        level = LogLevel.ALL
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 5_000
        connectTimeoutMillis = 5_000
        socketTimeoutMillis = 5_000
    }
}

// 平台特定实现
expect fun createHttpClient(): HttpClient

// JSON 序列化配置
internal val json = kotlinx.serialization.json.Json {
    ignoreUnknownKeys = true
    explicitNulls = false
    prettyPrint = true
}