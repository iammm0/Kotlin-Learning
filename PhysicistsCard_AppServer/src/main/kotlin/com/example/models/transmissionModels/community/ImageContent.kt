package com.example.models.transmissionModels.community

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("ImageContent")
data class ImageContent(
    override val type: ContentType = ContentType.ImageContent,
    override val contentId: Int,
    override val order: Int,
    val imageUrl: String
) : Content()