package com.example.models.transmissionModels.auth

import com.example.utils.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class AuthToken(
    val userId: String,
    val token: String,
    @Serializable(with = InstantSerializer::class)
    val expiryDate: Instant, // 令牌过期时间
    @Serializable(with = InstantSerializer::class)
    val issuedAt: Instant // 令牌发行时间
)