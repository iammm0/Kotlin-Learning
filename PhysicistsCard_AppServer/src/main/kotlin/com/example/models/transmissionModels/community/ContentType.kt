package com.example.models.transmissionModels.community

import kotlinx.serialization.Serializable

@Serializable
enum class ContentType {
    TextContent, ImageContent, VideoContent
}
