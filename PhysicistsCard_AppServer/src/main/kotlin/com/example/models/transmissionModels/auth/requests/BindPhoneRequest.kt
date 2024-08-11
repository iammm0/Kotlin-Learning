package com.example.models.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class BindPhoneRequest(
    val newPhone: String,
    val oldPhoneVerificationCode: String?
)