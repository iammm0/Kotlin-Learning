package com.example.repositories.community

import com.example.models.transmissionModels.community.FavoriteTargetType
import com.example.models.transmissionModels.community.UserFavorite

interface IUserFavoriteRepository {
    fun addFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite
    // 用户收藏帖子或商品。参数类似点赞功能。

    fun removeFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): Boolean
    // 用户取消收藏。操作参数同上。

    fun findFavoritesByUserId(userId: String): List<UserFavorite>
    // 根据用户ID查找该用户的所有收藏记录，用于展示用户收藏的帖子或商品。
}