package com.example.models.transmissionModels.community.post

import kotlinx.serialization.Serializable

@Serializable
sealed class Content {
    abstract val contentId: Int
    abstract val order: Int
    abstract val type: ContentType
}