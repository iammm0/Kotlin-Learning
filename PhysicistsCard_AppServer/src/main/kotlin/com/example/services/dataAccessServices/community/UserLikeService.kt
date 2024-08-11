package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.interaction.LikeTargetType
import com.example.models.transmissionModels.community.interaction.UserLike
import com.example.repositories.community.IUserLikeRepository

class UserLikeService(private val userLikeRepository: IUserLikeRepository) : IUserLikeService {
    override fun addLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike? {
        val existingLike = userLikeRepository.findLike(userId, targetId, targetType)
        return if (existingLike == null) {
            userLikeRepository.addLike(userId, targetId, targetType)
        } else {
            null  // 返回null表示已存在点赞记录
        }
    }

    override fun removeLike(userId: String, targetId: String, targetType: LikeTargetType): Boolean {
        return userLikeRepository.removeLike(userId, targetId, targetType)
    }

    override fun getLikesByUserId(userId: String): List<UserLike> {
        return userLikeRepository.findLikesByUserId(userId)
    }

    override fun countLikes(targetId: String, targetType: LikeTargetType): Int {
        return userLikeRepository.countLikes(targetId, targetType)
    }
}
