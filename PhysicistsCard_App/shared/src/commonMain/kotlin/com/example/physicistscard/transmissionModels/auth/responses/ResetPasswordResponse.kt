package com.example.physicistscard.transmissionModels.auth.responses

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordResponse(
    val success: Boolean, // 指示验证码发送是否成功
    val message: String, // 服务器返回的消息，例如成功或错误信息
    val errorCode: String? = null // 可选，用于错误处理的错误代码
)