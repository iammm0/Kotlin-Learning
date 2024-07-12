package com.example.repositories.auth

import com.example.models.transmissionModels.auth.tokens.AuthToken
import com.example.models.transmissionModels.auth.user.User
import java.time.LocalDateTime

interface IAuthTokenRepository {
    fun createToken(user: User, token: String, expiryDate: LocalDateTime, issuedAt: LocalDateTime): Boolean
    fun findToken(token: String): AuthToken?
    fun deleteToken(token: String): Boolean
    fun deleteExpiredTokens(): Boolean
}