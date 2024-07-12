package com.example.physicistscard.transmissionModels.auth.verificationCodes

import com.example.physicistscard.utils.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class VerificationCode(
    val code: String,
    val target: String,
    @Serializable(with = InstantSerializer::class)
    val expiryDate: Instant, // 验证码过期时间
    val type: VerificationType, // 验证码类型
    val isUsed: Boolean
)
