package com.ax.tototoproj.network

/**
 * @author : lihuiwu
 * @date : 2025/8/12
 * @desc :
 */
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import com.ax.tototoproj.network.DummyResponse
import com.ax.tototoproj.network.Post
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

// 通用配置
internal fun HttpClientConfig<*>.configureCommon() {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            prettyPrint = true

//            // 正确注册序列化器
//            serializersModule = SerializersModule {
//                // 关键修复：传入序列化器实例，而不是类型引用
//                contextual(Post::class, PostSerializer)
//
//                // 如果使用其他自定义序列化器
//                contextual(DummyResponse::class, DummyResponseSerializer)
//            }
        })
    }
    install(Logging) {
        level = LogLevel.ALL
    }
    // 添加超时设置
    install(HttpTimeout) {
        requestTimeoutMillis = 30000L // 30秒超时
        connectTimeoutMillis = 30000L
        socketTimeoutMillis = 30000L
    }
    // 自动重试机制
    install(HttpRequestRetry) {
        maxRetries = 2
        retryOnServerErrors(maxRetries = 1)
        exponentialDelay()
    }
}

// 平台特定实现
expect fun createHttpClient(): HttpClient
