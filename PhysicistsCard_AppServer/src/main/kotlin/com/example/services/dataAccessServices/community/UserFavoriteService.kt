package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.interaction.FavoriteTargetType
import com.example.models.transmissionModels.community.interaction.UserFavorite
import com.example.repositories.community.IUserFavoriteRepository
import com.example.repositories.community.UserFavoriteRepository

class UserFavoriteService(private val userFavoriteRepository: IUserFavoriteRepository) : IUserFavoriteService {
    override fun addFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite? {
        val existingFavorite = userFavoriteRepository.findFavorite(userId, targetId, targetType)
        return if (existingFavorite == null) {
            userFavoriteRepository.addFavorite(userId, targetId, targetType)
        } else {
            null  // 返回null表示已存在收藏记录
        }
    }

    override fun removeFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): Boolean {
        return userFavoriteRepository.removeFavorite(userId, targetId, targetType)
    }

    override fun getFavoritesByUserId(userId: String): List<UserFavorite> {
        return userFavoriteRepository.findFavoritesByUserId(userId)
    }

    override fun countFavoritesByTargetId(targetId: String, targetType: FavoriteTargetType): Int {
        return userFavoriteRepository.findFavoritesByTargetId(targetId, targetType).size
    }

}
