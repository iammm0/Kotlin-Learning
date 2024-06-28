package com.example.physicistscard.transmissionModels.community.post

import kotlinx.serialization.Serializable

@Serializable
data class PostTag(
    val tagId: String,
    val name: String
)