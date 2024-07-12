package com.example.physicistscard.transmissionModels.auth.tokens

import com.example.physicistscard.utils.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    val userId: String,
    val token: String,
    @kotlinx.serialization.Serializable(with = InstantSerializer::class)
    val expiryDate: Instant, // 令牌过期时间
    @kotlinx.serialization.Serializable(with = InstantSerializer::class)
    val issuedAt: Instant // 令牌发行时间
)