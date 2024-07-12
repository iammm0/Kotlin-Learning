package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.interaction.LikeTargetType
import com.example.models.transmissionModels.community.interaction.UserLike
import com.example.repositories.community.IUserLikeRepository
import com.example.repositories.community.UserLikeRepository

class UserLikeService(private val userLikeRepository: IUserLikeRepository) : IUserLikeService {
    override fun addLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike {
        TODO("Not yet implemented")
    }

    override fun removeLike(userId: String, targetId: String, targetType: LikeTargetType): Boolean {
        TODO("Not yet implemented")
    }

    override fun getLikesByUserId(userId: String): List<UserLike> {
        TODO("Not yet implemented")
    }

    override fun countLikes(targetId: String, targetType: LikeTargetType): Int {
        TODO("Not yet implemented")
    }

    override fun add(item: UserLike): UserLike {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): UserLike? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<UserLike?> {
        TODO("Not yet implemented")
    }

    override fun update(item: UserLike): UserLike {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Boolean {
        TODO("Not yet implemented")
    }


}
