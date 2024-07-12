package com.example.physicistscard.transmissionModels.auth.responses

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(
    val success: Boolean,
    val message: String,
    val userId: String?, // 用户注册成功后，可能会返回用户ID
    val token: String?
)