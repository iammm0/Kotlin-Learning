package com.example.physicistscard.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class PasswordLoginRequest(
    val identifier: String,
    val password: String
) : com.example.physicistscard.transmissionModels.auth.requests.LoginRequest()