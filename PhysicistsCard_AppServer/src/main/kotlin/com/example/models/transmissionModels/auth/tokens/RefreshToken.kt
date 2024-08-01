package com.example.models.transmissionModels.auth.tokens

import java.time.LocalDateTime

data class RefreshToken(
    val userId: String,
    val token: String,
    val createdAt: LocalDateTime,
    val expiresAt: LocalDateTime
)
