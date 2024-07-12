package com.example.models.transmissionModels.community.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("VideoContent")
data class VideoContent(
    override val contentId: Int,
    override val order: Int,
    override val type: ContentType = ContentType.VideoContent,
    val videoUrl: String
) : Content()