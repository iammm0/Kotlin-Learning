package com.example.physicistscard.transmissionModels.auth.requests

import com.example.physicistscard.transmissionModels.auth.user.Role
import kotlinx.serialization.Serializable

@Serializable
data class AddAccountRequest(
    val username: String,
    val email: String? = null,
    val phone: String? = null,
    val password: String,
    val role: Role
)
