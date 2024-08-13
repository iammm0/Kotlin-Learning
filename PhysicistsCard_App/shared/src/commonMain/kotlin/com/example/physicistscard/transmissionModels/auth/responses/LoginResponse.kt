package com.example.physicistscard.transmissionModels.auth.responses

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val success: Boolean,
    val token: String?, // 登录成功时服务器返回的认证令牌
    val refreshToken: String?,
    val errorMessage: String? // 登录失败时返回的错误信息
)