package com.example.physicistscard.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoUpdateRequest(
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val avatarUrl: String? = null,
    val bio: String? = null
)
