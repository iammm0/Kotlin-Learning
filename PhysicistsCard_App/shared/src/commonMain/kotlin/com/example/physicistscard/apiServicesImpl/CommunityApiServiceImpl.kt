package com.example.physicistscard.apiServicesImpl

import com.example.physicistscard.apiServices.CommunityApiService
import com.example.physicistscard.transmissionModels.community.interaction.comment.UserComment
import com.example.physicistscard.transmissionModels.community.interaction.favorite.UserFavorite
import com.example.physicistscard.transmissionModels.community.interaction.like.UserLike
import com.example.physicistscard.transmissionModels.community.post.Post
import io.ktor.client.*

class CommunityApiServiceImpl(private val client: HttpClient) : CommunityApiService {
    private val baseUrl = "http://127.0.0.1:8080"

    override suspend fun getAllPosts(): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostById(postId: String): Post? {
        TODO("Not yet implemented")
    }

    override suspend fun addPost(post: Post): Post {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(postId: String, post: Post): Post {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addComment(postId: String, comment: UserComment): UserComment {
        TODO("Not yet implemented")
    }

    override suspend fun getCommentsByPostId(postId: String): List<UserComment> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteComment(commentId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateComment(commentId: String, comment: UserComment): UserComment {
        TODO("Not yet implemented")
    }

    override suspend fun addLike(postId: String, userId: String): UserLike {
        TODO("Not yet implemented")
    }

    override suspend fun removeLike(postId: String, userId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addFavorite(postId: String, userId: String): UserFavorite {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavorite(postId: String, userId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getPostStats(postId: String): Map<String, Any> {
        TODO("Not yet implemented")
    }

}