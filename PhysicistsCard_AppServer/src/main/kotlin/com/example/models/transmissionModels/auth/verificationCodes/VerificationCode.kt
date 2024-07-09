package com.example.models.transmissionModels.auth

import com.example.utils.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class VerificationCode(
    val code: String,
    val target: String,
    @Serializable(with = InstantSerializer::class)
    val expiryDate: Instant, // 验证码过期时间
    val type: VerificationType, // 验证码类型
    val isUsed: Boolean
)
