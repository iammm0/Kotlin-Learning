package com.example.models.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class BindEmailRequest(
    val newEmail: String,
    val oldEmailVerificationCode: String?
)