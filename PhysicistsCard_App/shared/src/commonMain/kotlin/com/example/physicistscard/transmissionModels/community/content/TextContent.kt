package com.example.physicistscard.transmissionModels.community.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 确保发送的 JSON 请求体中 type 字段的值与 SerialName 注解中的值一致。

@Serializable
@SerialName("TextContent")
data class TextContent(
    override val contentId: Int,
    override val order: Int,
    override val type: ContentType = ContentType.TextContent,
    val text: String
) : Content()