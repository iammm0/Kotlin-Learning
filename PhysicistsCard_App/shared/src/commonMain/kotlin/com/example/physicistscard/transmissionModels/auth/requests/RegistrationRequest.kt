package com.example.physicistscard.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    val email: String? = null,
    val phone: String? = null,
    val password: String,
    val code: String,
)

