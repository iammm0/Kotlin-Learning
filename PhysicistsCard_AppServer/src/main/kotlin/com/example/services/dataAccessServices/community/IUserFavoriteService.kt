package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.FavoriteTargetType
import com.example.models.transmissionModels.community.UserFavorite
import com.example.repositories.Repository

interface IUserFavoriteService : Repository<UserFavorite, String> {
    fun addFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite
    fun removeFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): Boolean
    fun getFavoritesByUserId(userId: String): List<UserFavorite>
}
