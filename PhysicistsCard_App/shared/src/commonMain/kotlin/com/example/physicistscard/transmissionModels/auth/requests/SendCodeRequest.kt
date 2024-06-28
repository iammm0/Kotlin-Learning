package com.example.physicistscard.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class SendCodeRequest(
    val identifier: String, // 可以是邮箱或手机号
    val operation: String
)

