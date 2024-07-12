package com.example.physicistscard.transmissionModels.auth.tokens

import kotlinx.serialization.Serializable

@Serializable
data class AuthResult(
    val success: Boolean,
    val token: String?,
    val refreshToken: String?,
    val message: String?
    // 认证操作的结果，包括成功与否、令牌信息和相关消息。
)