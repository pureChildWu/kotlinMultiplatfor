package com.ax.tototoproj.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// shared/src/commonMain/kotlin/network/ApiService.kt
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService {
    /**
     * 创建 HTTP 客户端实例。
     */
    private val client = createHttpClient()

    /**
     * 悬挂函数，用于获取帖子列表。
     *
     * @return 帖子列表。
     */
    suspend fun getPosts(): List<Post> {
        return client.get("https://jsonplaceholder.typicode.com/posts").body()
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
}

@Serializable
data class Post(
    val id: Int? = null,
    val title: String,
    val body: String,
    val userId: Int
)