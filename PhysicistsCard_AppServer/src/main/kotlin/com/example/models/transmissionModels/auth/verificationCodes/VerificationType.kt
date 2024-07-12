package com.example.models.transmissionModels.auth.verificationCodes

import kotlinx.serialization.Serializable

@Serializable
enum class VerificationType {
    EMAIL_REGISTER,
    PHONE_REGISTER,
    RESET_PASSWORD,
    EMAIL_LOGIN,
    PHONE_LOGIN
}