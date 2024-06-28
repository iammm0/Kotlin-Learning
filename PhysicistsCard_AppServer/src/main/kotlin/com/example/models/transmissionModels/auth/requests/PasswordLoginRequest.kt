package com.example.models.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class PasswordLoginRequest(
    val identifier: String,
    val password: String
) : LoginRequest()