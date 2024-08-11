package com.example.repositories.community

import com.example.models.transmissionModels.community.interaction.LikeTargetType
import com.example.models.transmissionModels.community.interaction.UserLike

// 添加和移除点赞和收藏，同时支持按用户或目标ID查询这些互动记录
interface IUserLikeRepository {
    /**
     * 用户对帖子或商品进行点赞
     *
     * @param userId 用户ID
     * @param targetId 目标ID
     * @param targetType 目标类型（帖子或商品）
     * @return 点赞对象
     */
    fun addLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike?

    /**
     * 用户取消点赞
     *
     * @param userId 用户ID
     * @param targetId 目标ID
     * @param targetType 目标类型（帖子或商品）
     * @return 取消点赞是否成功
     */
    fun removeLike(userId: String, targetId: String, targetType: LikeTargetType): Boolean

    /**
     * 根据用户ID查找该用户的所有点赞记录
     *
     * @param userId 用户ID
     * @return 点赞记录列表
     */
    fun findLikesByUserId(userId: String): List<UserLike>

    /**
     * 计算特定帖子或商品的总点赞数
     *
     * @param targetId 目标ID
     * @param targetType 目标类型（帖子或商品）
     * @return 点赞总数
     */
    fun countLikes(targetId: String, targetType: LikeTargetType): Int

    /**
     * 查找用户是否已对特定目标点赞
     *
     * @param userId 用户ID
     * @param targetId 目标ID
     * @param targetType 目标类型（帖子或商品）
     * @return 点赞对象或null
     */
    fun findLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike? // 新增的方法
}
