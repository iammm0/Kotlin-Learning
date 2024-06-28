package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.LikeTargetType
import com.example.models.transmissionModels.community.UserLike
import com.example.repositories.Repository

interface IUserLikeService : Repository<UserLike, String> {
    fun addLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike
    fun removeLike(userId: String, targetId: String, targetType: LikeTargetType): Boolean
    fun getLikesByUserId(userId: String): List<UserLike>
    fun countLikes(targetId: String, targetType: LikeTargetType): Int
}
