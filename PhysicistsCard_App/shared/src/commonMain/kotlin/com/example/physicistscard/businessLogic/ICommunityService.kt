package com.example.physicistscard.businessLogic

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
import kotlinx.uuid.UUID

interface ICommunityService {

    /**
     * 获取所有社区推送
     * @return 所有推送列表
     */
    suspend fun getAllPosts(): Result<List<Post>>

    /**
     * 根据ID查找推送
     * @param postId 推送ID
     * @return 推送详情
     */
    suspend fun findPostById(postId: UUID): Result<Post?>

    /**
     * 创建新推送
     * @param userId 用户ID
     * @param title 推送标题
     * @param contents 推送内容
     * @param category 推送分类
     * @param tags 推送标签
     * @return 创建的推送对象
     */
    suspend fun createPost(userId: String, title: String, contents: List<Content>, category: String?, tags: List<String>): Result<Post>

    /**
     * 更新推送内容
     * @param postId 推送ID
     * @param updateRequest 更新请求对象，包含标题、内容和分类
     * @return 是否成功更新
     */
    suspend fun updatePost(postId: UUID, updateRequest: UpdatePostRequest): Result<Boolean>

    /**
     * 删除推送
     * @param postId 推送ID
     * @return 是否成功删除
     */
    suspend fun deletePost(postId: UUID): Result<Boolean>

    /**
     * 在特定推送下发表评论
     * @param postId 推送ID
     * @param comment 评论对象
     * @return 是否成功添加评论
     */
    suspend fun addComment(postId: String, comment: UserComment): Result<Boolean>

    /**
     * 获取特定推送下的所有评论
     * @param postId 推送ID
     * @param targetType 评论目标类型
     * @return 评论列表
     */
    suspend fun getCommentsByTargetId(postId: String, targetType: CommentTargetType): Result<List<UserComment>>

    /**
     * 删除特定评论
     * @param commentId 评论ID
     * @return 是否成功删除评论
     */
    suspend fun deleteComment(commentId: String): Result<Boolean>

    /**
     * 为特定推送点赞
     * @param userId 用户ID
     * @param postId 推送ID
     * @param targetType 点赞目标类型
     * @return 点赞对象
     */
    suspend fun addLike(userId: String, postId: String, targetType: LikeTargetType): Result<UserLike?>

    /**
     * 取消对特定推送的点赞
     * @param userId 用户ID
     * @param postId 推送ID
     * @param targetType 点赞目标类型
     * @return 是否成功取消点赞
     */
    suspend fun removeLike(userId: String, postId: String, targetType: LikeTargetType): Result<Boolean>

    /**
     * 为特定推送添加收藏
     * @param userId 用户ID
     * @param postId 推送ID
     * @param targetType 收藏目标类型
     * @return 收藏对象
     */
    suspend fun addFavorite(userId: String, postId: String, targetType: FavoriteTargetType): Result<UserFavorite?>

    /**
     * 取消对特定推送的收藏
     * @param userId 用户ID
     * @param postId 推送ID
     * @param targetType 收藏目标类型
     * @return 是否成功取消收藏
     */
    suspend fun removeFavorite(userId: String, postId: String, targetType: FavoriteTargetType): Result<Boolean>

    /**
     * 获取特定推送的统计信息
     * @param postId 推送ID
     * @return 推送统计信息
     */
    suspend fun getPostStats(postId: UUID): Result<PostStat?>
}

