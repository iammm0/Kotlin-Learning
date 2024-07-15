package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.interaction.FavoriteTargetType
import com.example.models.transmissionModels.community.interaction.UserFavorite
import com.example.repositories.Repository

interface IUserFavoriteService {
    /**
     * 添加收藏
     *
     * @param userId 用户ID
     * @param targetId 目标对象ID
     * @param targetType 目标对象类型
     * @return 新增的收藏对象
     */
    fun addFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite

    /**
     * 移除收藏
     *
     * @param userId 用户ID
     * @param targetId 目标对象ID
     * @param targetType 目标对象类型
     * @return 移除收藏是否成功
     */
    fun removeFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): Boolean

    /**
     * 获取用户的所有收藏
     *
     * @param userId 用户ID
     * @return 收藏列表
     */
    fun getFavoritesByUserId(userId: String): List<UserFavorite>

    /**
     * 统计特定目标对象的收藏数
     *
     * @param targetId 目标对象ID
     * @param targetType 目标对象类型
     * @return 收藏数
     */
    fun countFavoritesByTargetId(targetId: String, targetType: FavoriteTargetType): Int
}
