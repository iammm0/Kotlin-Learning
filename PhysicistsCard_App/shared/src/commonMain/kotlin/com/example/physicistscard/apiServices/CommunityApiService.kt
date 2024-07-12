package com.example.physicistscard.apiServices

import Post
import UserComment
import UserFavorite
import UserLike

interface CommunityApiService {
    // Post APIs
    suspend fun getAllPosts(): List<Post>
    suspend fun getPostById(postId: String): Post?
    suspend fun addPost(post: Post): Post
    suspend fun updatePost(postId: String, post: Post): Post
    suspend fun deletePost(postId: String): Boolean

    // Comment APIs
    suspend fun addComment(postId: String, comment: UserComment): UserComment
    suspend fun getCommentsByPostId(postId: String): List<UserComment>
    suspend fun deleteComment(commentId: String): Boolean
    suspend fun updateComment(commentId: String, comment: UserComment): UserComment

    // Like APIs
    suspend fun addLike(postId: String, userId: String): UserLike
    suspend fun removeLike(postId: String, userId: String): Boolean

    // Favorite APIs
    suspend fun addFavorite(postId: String, userId: String): UserFavorite
    suspend fun removeFavorite(postId: String, userId: String): Boolean

    // Stats APIs
    suspend fun getPostStats(postId: String): Map<String, Any>
}
