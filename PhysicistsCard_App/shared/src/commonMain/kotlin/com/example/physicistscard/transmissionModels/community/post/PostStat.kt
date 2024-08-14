package com.example.physicistscard.transmissionModels.community.post

import com.example.physicistscard.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

@Serializable
data class PostStat(
    @Serializable(with = UUIDSerializer::class)
    val postId: UUID,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val favoritesCount: Int = 0
)
