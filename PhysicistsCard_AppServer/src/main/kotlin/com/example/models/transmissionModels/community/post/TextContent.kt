package com.example.models.transmissionModels.community.post

import com.example.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

// 确保发送的 JSON 请求体中 type 字段的值与 SerialName 注解中的值一致。

@Serializable
@SerialName("TextContent")
data class TextContent(
    @Serializable(with = UUIDSerializer::class)
    override val contentId: UUID,
    override val order: Int,
    override val type: ContentType = ContentType.TextContent,
    val text: String
) : Content()