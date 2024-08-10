package com.example.models.transmissionModels.community.post

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
sealed class Content {
    abstract val contentId: UUID
    abstract val order: Int
    abstract val type: ContentType
}