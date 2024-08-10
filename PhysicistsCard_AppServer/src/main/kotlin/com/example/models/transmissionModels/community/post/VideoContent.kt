package com.example.models.transmissionModels.community.post

import com.example.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("VideoContent")
data class VideoContent(
    @Serializable(with = UUIDSerializer::class)
    override val contentId: UUID,
    override val order: Int,
    override val type: ContentType = ContentType.VideoContent,
    val videoUrl: String
) : Content()