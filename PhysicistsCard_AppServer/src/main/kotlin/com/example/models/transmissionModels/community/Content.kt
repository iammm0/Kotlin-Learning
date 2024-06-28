package com.example.models.transmissionModels.community

import kotlinx.serialization.Serializable

@Serializable
sealed class Content {
    abstract val contentId: Int
    abstract val order: Int
    abstract val type: ContentType
}