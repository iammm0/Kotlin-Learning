package com.example.models.transmissionModels.community.post

import com.example.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
@SerialName("ImageContent")
data class ImageContent(
    override val type: ContentType = ContentType.ImageContent,
    @Serializable(with = UUIDSerializer::class)
    override val contentId: UUID,
    override val order: Int,
    val imageUrl: String
) : Content()