package com.example.repositories.community

import com.example.models.transmissionModels.community.interaction.FavoriteTargetType
import com.example.models.transmissionModels.community.interaction.UserFavorite

interface IUserFavoriteRepository {
    /**
     * 用户收藏帖子或商品
     *
     * @param userId 用户ID
     * @param targetId 目标ID
     * @param targetType 目标类型（帖子或商品）
     * @return 收藏对象
     */
    fun addFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite

    /**
     * 用户取消收藏
     *
     * @param userId 用户ID
     * @param targetId 目标ID
     * @param targetType 目标类型（帖子或商品）
     * @return 取消收藏是否成功
     */
    fun removeFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): Boolean

    /**
     * 根据用户ID查找该用户的所有收藏记录
     *
     * @param userId 用户ID
     * @return 收藏记录列表
     */
    fun findFavoritesByUserId(userId: String): List<UserFavorite>
}
