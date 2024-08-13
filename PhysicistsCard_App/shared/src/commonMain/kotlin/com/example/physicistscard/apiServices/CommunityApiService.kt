package com.example.physicistscard.apiServices

import CommentTargetType
import FavoriteTargetType
import LikeTargetType
import Post
import UserComment
import UserFavorite
import UserLike
import com.example.physicistscard.transmissionModels.community.UpdatePostRequest
import com.example.physicistscard.transmissionModels.community.content.Content
import com.example.physicistscard.transmissionModels.community.post.PostStat
import com.example.physicistscard.utils.TokenManager
import com.example.physicistscard.utils.safeBodyOrNull
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.*
import kotlinx.uuid.UUID

class CommunityApiService(private val client: HttpClient) : ICommunityApiService {

    private val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    override suspend fun getAllPosts(): List<Post> {
        return client.get {
            url("${NetworkConfig.BASE_URL}/community/posts")
        }.body()
    }

    override suspend fun findPostById(postId: UUID): Post? {
        return client.get {
            url("${NetworkConfig.BASE_URL}/community/posts/$postId")
        }.body()
    }

    override suspend fun createPost(
        userId: String,
        title: String,
        contents: List<Content>, // 修改为使用 List<Content>
        category: String?,
        tags: List<String>,
        tokenManager: TokenManager
    ): Post {
        val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
        return client.post {
            url("${NetworkConfig.BASE_URL}/community/posts")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(
                Post(
                    postId = UUID().toString(),  // 生成新的 UUID
                    userId = userId,
                    title = title,
                    contents = contents,
                    createdAt = currentDateTime,
                    updatedAt = null,
                    category = category,
                    tags = tags
                )
            )
        }.body()
    }

    override suspend fun updatePost(
        postId: UUID,
        updateRequest: UpdatePostRequest,
        tokenManager: TokenManager
    ): Boolean {
        val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
        return client.put {
            url("${NetworkConfig.BASE_URL}/community/posts/$postId")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(updateRequest) // 直接传递 UpdatePostRequest 对象
        }.status == HttpStatusCode.OK
    }


    override suspend fun deletePost(postId: UUID, tokenManager: TokenManager): Boolean {
        return client.delete {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            url("${NetworkConfig.BASE_URL}/community/posts/$postId")
            header(HttpHeaders.Authorization, "Bearer $token")
        }.status == HttpStatusCode.OK
    }

    override suspend fun addComment(postId: String, comment: UserComment, tokenManager: TokenManager): Boolean {
        return client.post {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            url("${NetworkConfig.BASE_URL}/community/comments/$postId")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(comment)
        }.status == HttpStatusCode.Created
    }

    override suspend fun getCommentsByTargetId(postId: String, targetType: CommentTargetType, tokenManager: TokenManager): List<UserComment> {
        return client.get {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            url("${NetworkConfig.BASE_URL}/community/comments/$postId")
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("targetType", targetType.name) // 使用 targetType 的字符串表示
        }.body()
    }

    override suspend fun deleteComment(commentId: String, tokenManager: TokenManager): Boolean {
        return client.delete {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            url("${NetworkConfig.BASE_URL}/community/comments/$commentId")
            header(HttpHeaders.Authorization, "Bearer $token")
        }.status == HttpStatusCode.OK
    }

    override suspend fun addLike(userId: String, postId: String, targetType: LikeTargetType, tokenManager: TokenManager): UserLike? {
        return client.post {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            url("${NetworkConfig.BASE_URL}/community/posts/$postId/like")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(
                UserLike(
                    likeId = UUID().toString(),  // 生成新的 UUID
                    userId = userId,
                    targetId = postId,
                    targetType = targetType,
                    createdAt = currentDateTime
                )
            )
        }.safeBodyOrNull()
    }

    override suspend fun removeLike(userId: String, postId: String, targetType: LikeTargetType, tokenManager: TokenManager): Boolean {
        return client.delete {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            url("${NetworkConfig.BASE_URL}/community/posts/$postId/like")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(
                UserLike(
                    likeId = UUID().toString(),  // 虽然删除操作不一定需要 likeId，但这里作为示例提供
                    userId = userId,
                    targetId = postId,
                    targetType = targetType,
                    createdAt = currentDateTime
                )
            )
        }.status == HttpStatusCode.OK
    }

    override suspend fun addFavorite(userId: String, postId: String, targetType: FavoriteTargetType, tokenManager: TokenManager): UserFavorite? {
        return client.post {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            url("${NetworkConfig.BASE_URL}/community/posts/$postId/favorite")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(
                UserFavorite(
                    favoriteId = UUID().toString(),  // 生成新的 UUID
                    userId = userId,
                    targetId = postId,
                    targetType = targetType,
                    createdAt = currentDateTime
                )
            )
        }.safeBodyOrNull()
    }

    override suspend fun removeFavorite(userId: String, postId: String, targetType: FavoriteTargetType, tokenManager: TokenManager): Boolean {
        return client.delete {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            url("${NetworkConfig.BASE_URL}/community/posts/$postId/favorite")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(
                UserFavorite(
                    favoriteId = UUID().toString(),
                    userId = userId,
                    targetId = postId,
                    targetType = targetType,
                    createdAt = currentDateTime
                )
            )
        }.status == HttpStatusCode.OK
    }

    override suspend fun getPostStats(postId: UUID, tokenManager: TokenManager): PostStat? {
        return client.get {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            url("${NetworkConfig.BASE_URL}/community/posts/$postId/stats")
            header(HttpHeaders.Authorization, "Bearer $token")
        }.safeBodyOrNull()
    }
}
