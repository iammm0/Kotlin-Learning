package com.example.physicistscard.transmissionModels.auth.authToken

import kotlinx.datetime.Instant

data class AuthToken(
    val userId: String,
    val token: String,
    val expiryDate: Instant, // 令牌过期时间
    val issuedAt: Instant // 令牌发行时间
)