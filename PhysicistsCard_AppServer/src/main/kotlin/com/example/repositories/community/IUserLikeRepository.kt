package com.example.repositories.community

import com.example.models.transmissionModels.community.LikeTargetType
import com.example.models.transmissionModels.community.UserLike

// 添加和移除点赞和收藏，同时支持按用户或目标ID查询这些互动记录
interface IUserLikeRepository {
    fun addLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike
    // 用户对帖子或商品进行点赞。需要指定用户ID、目标ID及目标类型（帖子或商品）。

    fun removeLike(userId: String, targetId: String, targetType: LikeTargetType): Boolean
    // 用户取消点赞。操作参数同上

    fun findLikesByUserId(userId: String): List<UserLike>
    // 根据用户ID查找该用户的所有点赞记录，用于展示用户赞过的帖子或商品。

    fun countLikes(targetId: String, targetType: LikeTargetType): Int
    // 计算特定帖子或商品的总点赞数，用于展示点赞总数。
}