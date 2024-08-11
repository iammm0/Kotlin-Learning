package com.example.models.transmissionModels.community.post

import com.example.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PostStat(
    @Serializable(with = UUIDSerializer::class)
    val postId: UUID,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val favoritesCount: Int = 0
)
