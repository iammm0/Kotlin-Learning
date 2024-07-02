package com.example.models.transmissionModels.auth.requests

import com.example.models.transmissionModels.auth.Role
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    val email: String? = null,
    val phone: String? = null,
    val password: String,
    val code: String,
    val role: Role
)

