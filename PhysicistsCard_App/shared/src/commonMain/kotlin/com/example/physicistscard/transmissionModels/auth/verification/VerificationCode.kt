package com.example.physicistscard.transmissionModels.auth.verification

import kotlinx.datetime.Instant


data class VerificationCode(
    val code: String,
    val target: String,
    val expiryDate: Instant, // 验证码过期时间
    val type: com.example.physicistscard.transmissionModels.auth.verification.VerificationType, // 验证码类型
    val isUsed: Boolean
)
