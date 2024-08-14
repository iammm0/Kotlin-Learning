package com.example.physicistscard.android.viewModels

import CommentTargetType
import FavoriteTargetType
import LikeTargetType
import Post
import UserComment
import UserFavorite
import UserLike
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicistscard.businessLogic.ICommunityService
import com.example.physicistscard.transmissionModels.community.UpdatePostRequest
import com.example.physicistscard.transmissionModels.community.content.Content
import com.example.physicistscard.transmissionModels.community.post.PostStat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.uuid.UUID

class CommunityViewModel(
    private val communityService: ICommunityService
) : ViewModel() {

    // 所有推送的状态
    private val _allPostsState = MutableStateFlow<Result<List<Post>>?>(null)
    val allPostsState: StateFlow<Result<List<Post>>?> = _allPostsState

    // 单个推送的状态
    private val _postState = MutableStateFlow<Result<Post?>?>(null)
    val postState: StateFlow<Result<Post?>?> = _postState

    // 推送创建状态
    private val _createPostState = MutableStateFlow<Result<Post>?>(null)
    val createPostState: StateFlow<Result<Post>?> = _createPostState

    // 推送更新状态
    private val _updatePostState = MutableStateFlow<Result<Boolean>?>(null)
    val updatePostState: StateFlow<Result<Boolean>?> = _updatePostState

    // 推送删除状态
    private val _deletePostState = MutableStateFlow<Result<Boolean>?>(null)
    val deletePostState: StateFlow<Result<Boolean>?> = _deletePostState

    // 添加评论状态
    private val _addCommentState = MutableStateFlow<Result<Boolean>?>(null)
    val addCommentState: StateFlow<Result<Boolean>?> = _addCommentState

    // 获取评论状态
    private val _commentsState = MutableStateFlow<Result<List<UserComment>>?>(null)
    val commentsState: StateFlow<Result<List<UserComment>>?> = _commentsState

    // 点赞状态
    private val _likeState = MutableStateFlow<Result<UserLike?>?>(null)
    val likeState: StateFlow<Result<UserLike?>?> = _likeState

    // 取消点赞状态
    private val _removeLikeState = MutableStateFlow<Result<Boolean>?>(null)
    val removeLikeState: StateFlow<Result<Boolean>?> = _removeLikeState

    // 收藏状态
    private val _favoriteState = MutableStateFlow<Result<UserFavorite?>?>(null)
    val favoriteState: StateFlow<Result<UserFavorite?>?> = _favoriteState

    // 取消收藏状态
    private val _removeFavoriteState = MutableStateFlow<Result<Boolean>?>(null)
    val removeFavoriteState: StateFlow<Result<Boolean>?> = _removeFavoriteState

    // 获取推送统计信息状态
    private val _postStatsState = MutableStateFlow<Result<PostStat?>?>(null)
    val postStatsState: StateFlow<Result<PostStat?>?> = _postStatsState

    // 获取所有推送
    fun getAllPosts() {
        viewModelScope.launch {
            _allPostsState.value = try {
                val posts = communityService.getAllPosts()
                Result.success(posts.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 根据ID获取单个推送
    fun findPostById(postId: UUID) {
        viewModelScope.launch {
            _postState.value = try {
                val post = communityService.findPostById(postId)
                Result.success(post.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 创建新推送
    fun createPost(userId: String, title: String, contents: List<Content>, category: String?, tags: List<String>) {
        viewModelScope.launch {
            _createPostState.value = try {
                val post = communityService.createPost(userId, title, contents, category, tags)
                Result.success(post.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 更新推送内容
    fun updatePost(postId: UUID, updateRequest: UpdatePostRequest) {
        viewModelScope.launch {
            _updatePostState.value = try {
                val success = communityService.updatePost(postId, updateRequest)
                Result.success(success.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 删除推送
    fun deletePost(postId: UUID) {
        viewModelScope.launch {
            _deletePostState.value = try {
                val success = communityService.deletePost(postId)
                Result.success(success.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 在推送下添加评论
    fun addComment(postId: String, comment: UserComment) {
        viewModelScope.launch {
            _addCommentState.value = try {
                val success = communityService.addComment(postId, comment)
                Result.success(success.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 获取推送下的所有评论
    fun getCommentsByTargetId(postId: String, targetType: CommentTargetType) {
        viewModelScope.launch {
            _commentsState.value = try {
                val comments = communityService.getCommentsByTargetId(postId, targetType)
                Result.success(comments.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 点赞推送
    fun addLike(userId: String, postId: String, targetType: LikeTargetType) {
        viewModelScope.launch {
            _likeState.value = try {
                val like = communityService.addLike(userId, postId, targetType)
                Result.success(like.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 取消对推送的点赞
    fun removeLike(userId: String, postId: String, targetType: LikeTargetType) {
        viewModelScope.launch {
            _removeLikeState.value = try {
                val success = communityService.removeLike(userId, postId, targetType)
                Result.success(success.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 收藏推送
    fun addFavorite(userId: String, postId: String, targetType: FavoriteTargetType) {
        viewModelScope.launch {
            _favoriteState.value = try {
                val favorite = communityService.addFavorite(userId, postId, targetType)
                Result.success(favorite.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 取消对推送的收藏
    fun removeFavorite(userId: String, postId: String, targetType: FavoriteTargetType) {
        viewModelScope.launch {
            _removeFavoriteState.value = try {
                val success = communityService.removeFavorite(userId, postId, targetType)
                Result.success(success.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 获取推送的统计信息
    fun getPostStats(postId: UUID) {
        viewModelScope.launch {
            _postStatsState.value = try {
                val stats = communityService.getPostStats(postId)
                Result.success(stats.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 清除状态方法
    fun clearAllPostsState() {
        _allPostsState.value = null
    }

    fun clearPostState() {
        _postState.value = null
    }

    fun clearCreatePostState() {
        _createPostState.value = null
    }

    fun clearUpdatePostState() {
        _updatePostState.value = null
    }

    fun clearDeletePostState() {
        _deletePostState.value = null
    }

    fun clearAddCommentState() {
        _addCommentState.value = null
    }

    fun clearCommentsState() {
        _commentsState.value = null
    }

    fun clearLikeState() {
        _likeState.value = null
    }

    fun clearRemoveLikeState() {
        _removeLikeState.value = null
    }

    fun clearFavoriteState() {
        _favoriteState.value = null
    }

    fun clearRemoveFavoriteState() {
        _removeFavoriteState.value = null
    }

    fun clearPostStatsState() {
        _postStatsState.value = null
    }
}
