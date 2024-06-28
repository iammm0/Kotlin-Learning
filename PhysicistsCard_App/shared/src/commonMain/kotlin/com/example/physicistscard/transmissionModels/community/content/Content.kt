package com.example.physicistscard.transmissionModels.community.content

import kotlinx.serialization.Serializable

@Serializable
sealed class Content {
    abstract val contentId: Int
    abstract val order: Int
    abstract val type: ContentType
}