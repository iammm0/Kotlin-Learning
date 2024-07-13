package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.interaction.FavoriteTargetType
import com.example.models.transmissionModels.community.interaction.UserFavorite
import com.example.repositories.community.IUserFavoriteRepository
import com.example.repositories.community.UserFavoriteRepository

class UserFavoriteService : IUserFavoriteService {
    override fun addFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite {
        TODO("Not yet implemented")
    }

    override fun removeFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFavoritesByUserId(userId: String): List<UserFavorite> {
        TODO("Not yet implemented")
    }

    override fun countFavoritesByTargetId(targetId: String, targetType: FavoriteTargetType): Int {
        TODO("Not yet implemented")
    }

    override fun add(item: UserFavorite): UserFavorite {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): UserFavorite? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<UserFavorite?> {
        TODO("Not yet implemented")
    }

    override fun update(item: UserFavorite): UserFavorite {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Boolean {
        TODO("Not yet implemented")
    }
}
