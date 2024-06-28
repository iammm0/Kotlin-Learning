package com.example.physicistscard.transmissionModels.community.post

import kotlinx.serialization.Serializable

@Serializable
data class PostCategory(
    val categoryId: String,
    val name: String,
    val description: String?
)