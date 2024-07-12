package com.example.repositories.auth

import com.example.models.databaseTableModels.auth.authToken.AuthTokens
import com.example.models.transmissionModels.auth.tokens.AuthToken
import com.example.models.transmissionModels.auth.user.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class TokenRepository : IAuthTokenRepository {

    override fun createToken(user: User, token: String, expiryDate: LocalDateTime, issuedAt: LocalDateTime): Boolean {
        val expiryInstant = expiryDate.atZone(ZoneId.systemDefault()).toInstant()
        val issuedAtInstant = issuedAt.atZone(ZoneId.systemDefault()).toInstant()

        return try {
            transaction {
                addLogger(StdOutSqlLogger)

                AuthTokens.insert {
                    it[this.userId] = user.userId
                    it[this.token] = token
                    it[this.expiryDate] = expiryInstant
                    it[this.issuedAt] = issuedAtInstant
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun findToken(token: String): AuthToken? {
        return transaction {
            addLogger(StdOutSqlLogger)
            AuthTokens.selectAll().where { AuthTokens.token eq token }
                .mapNotNull {
                    AuthToken(
                        userId = it[AuthTokens.userId],
                        token = it[AuthTokens.token],
                        expiryDate = it[AuthTokens.expiryDate],
                        issuedAt = it[AuthTokens.issuedAt]
                    )
                }.singleOrNull()
        }
    }

    override fun deleteToken(token: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)

            AuthTokens.deleteWhere { AuthTokens.token eq token } > 0
        }
    }

    override fun deleteExpiredTokens(): Boolean {
        val now = Instant.now()
        return transaction {
            addLogger(StdOutSqlLogger)

            AuthTokens.deleteWhere { expiryDate lessEq now } > 0
        }
    }

    fun deleteTokensByUserId(userId: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            AuthTokens.deleteWhere { AuthTokens.userId eq userId } > 0
        }
    }
}
