package com.example.physicistscard.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val identifier: String,
    val newPassword: String,
    val code: String
)