package com.example.models.transmissionModels.community

import com.example.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class UserFavorite(
    val favoriteId: String,
    val userId: String, // 收藏操作的用户ID
    val targetId: String, // 被收藏的目标对象ID
    val targetType: FavoriteTargetType, // 收藏的目标类型，帮助区分收藏内容
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime // 收藏操作的时间
)