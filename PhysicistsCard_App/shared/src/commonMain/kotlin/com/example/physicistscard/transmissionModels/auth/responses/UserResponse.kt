package com.example.physicistscard.transmissionModels.auth.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val email: String?,
    val phone: String?,
    val nickname: String?,
    val avatarUrl: String?
)