package com.example.models.transmissionModels.community.post

import com.example.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Post(
    val postId: String,
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
















