package com.ax.tototoproj.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// shared/src/commonMain/kotlin/network/ApiService.kt
import io.ktor.client.call.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService {
    /**
     * 创建 HTTP 客户端实例。
     */
    private val client = createHttpClient()

//    /**
//     * 悬挂函数，用于获取帖子列表。
//     *
//     * @return 帖子列表。
//     */
//    suspend fun getPosts(): List<Post> {
//        return client.get("https://jsonplaceholder.typicode.com/posts").body()
//    }

    /**
     * 获取帖子列表 - 使用可靠的测试API
     */
    suspend fun getPostsList(): List<Post> {
        try {
            println("开始请求: https://dummyjson.com/posts?limit=10")
            // 添加 DummyResponse 类来匹配 API 返回结构
            val response = client.get("https://dummyjson.com/posts?limit=10").body<DummyResponse>()
            println("API 响应成功，获取到 ${response.posts.size} 条数据")
            return response.posts
        } catch (e: Exception) {
            return emptyList()
        }
    }

    /**
     * 悬挂函数，用于创建一个新的帖子。
     *
     * @param post 要创建的帖子对象。
     * @return 新创建的帖子对象。
     */
    suspend fun createPost(post: Post): Post {
        return client.post("https://jsonplaceholder.typicode.com/posts") {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()
    }

    /**
     * 创建新帖子 - 使用可靠的测试API
     */
    suspend fun createPostNew(post: Post): Post {
        return client.post("https://dummyjson.com/posts/add") {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()
    }
}

@Serializable
data class Post(
    val id: Int? = null,
    val title: String,
    val body: String,
    val userId: Int
)

// 确保序列化导入
@Serializable
data class DummyResponse(
    val posts: List<Post>,
    val total: Int,
    val skip: Int,
    val limit: Int
)