package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.FavoriteTargetType
import com.example.models.transmissionModels.community.UserFavorite
import com.example.repositories.community.UserFavoriteRepository

class UserFavoriteService(private val userFavoriteRepository: UserFavoriteRepository) {

    fun addFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite {
        return userFavoriteRepository.addFavorite(userId, targetId, targetType)
    }

    fun removeFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): Boolean {
        return userFavoriteRepository.removeFavorite(userId, targetId, targetType)
    }

    fun getFavoritesByUserId(userId: String): List<UserFavorite> {
        return userFavoriteRepository.findFavoritesByUserId(userId)
    }
}
