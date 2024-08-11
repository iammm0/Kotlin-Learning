package com.example.models.transmissionModels.community.post

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePostRequest(
    val title: String?, // 可选字段，更新标题
    val contents: List<Content>?, // 可选字段，更新内容
    val category: String?, // 可选字段，更新分类
    val tags: List<String>? // 可选字段，更新标签
)
