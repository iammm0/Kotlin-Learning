package com.example.models.transmissionModels.community.interaction

import kotlinx.serialization.Serializable

@Serializable
data class UserCommentRequest(
    val content: String,
    val parentId: String? = null
)
