package com.example.physicistscard.businessLogic

import CommentTargetType
import FavoriteTargetType
import LikeTargetType
import Post
import UserComment
import UserFavorite
import UserLike
import com.example.physicistscard.apiServices.ICommunityApiService
import com.example.physicistscard.transmissionModels.community.UpdatePostRequest
import com.example.physicistscard.transmissionModels.community.content.Content
import com.example.physicistscard.transmissionModels.community.post.PostStat
import com.example.physicistscard.utils.TokenManager
import kotlinx.uuid.UUID

class CommunityService(
    private val communityApiService: ICommunityApiService,
    private val tokenManager: TokenManager
) : ICommunityService {

    override suspend fun getAllPosts(): Result<List<Post>> {
        return try {
            val posts = communityApiService.getAllPosts()
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun findPostById(postId: UUID): Result<Post?> {
        return try {
            val post = communityApiService.findPostById(postId)
            Result.success(post)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createPost(
        userId: String,
        title: String,
        contents: List<Content>,
        category: String?,
        tags: List<String>
    ): Result<Post> {
        return try {
            val post = communityApiService.createPost(userId, title, contents, category, tags, tokenManager)
            Result.success(post)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePost(postId: UUID, updateRequest: UpdatePostRequest): Result<Boolean> {
        return try {
            val success = communityApiService.updatePost(postId, updateRequest, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePost(postId: UUID): Result<Boolean> {
        return try {
            val success = communityApiService.deletePost(postId, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addComment(postId: String, comment: UserComment): Result<Boolean> {
        return try {
            val success = communityApiService.addComment(postId, comment, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommentsByTargetId(postId: String, targetType: CommentTargetType): Result<List<UserComment>> {
        return try {
            val comments = communityApiService.getCommentsByTargetId(postId, targetType, tokenManager)
            Result.success(comments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteComment(commentId: String): Result<Boolean> {
        return try {
            val success = communityApiService.deleteComment(commentId, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addLike(userId: String, postId: String, targetType: LikeTargetType): Result<UserLike?> {
        return try {
            val like = communityApiService.addLike(userId, postId, targetType, tokenManager)
            Result.success(like)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeLike(userId: String, postId: String, targetType: LikeTargetType): Result<Boolean> {
        return try {
            val success = communityApiService.removeLike(userId, postId, targetType, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addFavorite(userId: String, postId: String, targetType: FavoriteTargetType): Result<UserFavorite?> {
        return try {
            val favorite = communityApiService.addFavorite(userId, postId, targetType, tokenManager)
            Result.success(favorite)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFavorite(userId: String, postId: String, targetType: FavoriteTargetType): Result<Boolean> {
        return try {
            val success = communityApiService.removeFavorite(userId, postId, targetType, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPostStats(postId: UUID): Result<PostStat?> {
        return try {
            val stats = communityApiService.getPostStats(postId, tokenManager)
            Result.success(stats)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

