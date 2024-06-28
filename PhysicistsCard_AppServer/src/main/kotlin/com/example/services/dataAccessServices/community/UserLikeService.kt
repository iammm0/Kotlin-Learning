package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.LikeTargetType
import com.example.models.transmissionModels.community.UserLike
import com.example.repositories.community.UserLikeRepository

class UserLikeService(private val userLikeRepository: UserLikeRepository) {

    fun addLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike {
        return userLikeRepository.addLike(userId, targetId, targetType)
    }

    fun removeLike(userId: String, targetId: String, targetType: LikeTargetType): Boolean {
        return userLikeRepository.removeLike(userId, targetId, targetType)
    }

    fun getLikesByUserId(userId: String): List<UserLike> {
        return userLikeRepository.findLikesByUserId(userId)
    }

    fun countLikes(targetId: String, targetType: LikeTargetType): Int {
        return userLikeRepository.countLikes(targetId, targetType)
    }
}
