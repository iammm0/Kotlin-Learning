package com.example.models.transmissionModels.community.post

import com.example.utils.LocalDateTimeSerializer
import com.example.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class Post(
    @Serializable(with = UUIDSerializer::class)
    val postId: UUID,
    val userId: String,
    val title: String,
    val contents: List<Content>, // 使用多态序列化
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime?,
    val category: String?, // 分类
    val tags: List<String> // 标签
)
















