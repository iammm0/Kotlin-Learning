package com.example.models.transmissionModels.community.post

import kotlinx.serialization.Serializable

@Serializable
enum class ContentType {
    TextContent, ImageContent, VideoContent
}
