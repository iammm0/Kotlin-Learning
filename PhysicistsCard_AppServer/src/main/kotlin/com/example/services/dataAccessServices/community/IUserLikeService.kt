package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.interaction.LikeTargetType
import com.example.models.transmissionModels.community.interaction.UserLike
import com.example.repositories.Repository

interface  IUserLikeService {
    /**
     * 添加点赞
     *
     * @param userId 用户ID
     * @param targetId 目标对象ID
     * @param targetType 目标对象类型
     * @return 新增的点赞对象
     */
    fun addLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike

    /**
     * 移除点赞
     *
     * @param userId 用户ID
     * @param targetId 目标对象ID
     * @param targetType 目标对象类型
     * @return 移除点赞是否成功
     */
    fun removeLike(userId: String, targetId: String, targetType: LikeTargetType): Boolean

    /**
     * 获取用户的所有点赞
     *
     * @param userId 用户ID
     * @return 点赞列表
     */
    fun getLikesByUserId(userId: String): List<UserLike>

    /**
     * 统计特定对象的点赞数量
     *
     * @param targetId 目标对象ID
     * @param targetType 目标对象类型
     * @return 点赞数量
     */
    fun countLikes(targetId: String, targetType: LikeTargetType): Int
}
