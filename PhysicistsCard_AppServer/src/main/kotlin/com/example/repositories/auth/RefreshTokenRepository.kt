package com.example.repositories.auth

import com.example.models.databaseTableModels.auth.authToken.RefreshTokens
import com.example.models.transmissionModels.auth.tokens.RefreshToken
import com.example.models.transmissionModels.auth.user.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class RefreshTokenRepository : IRefreshTokenRepository {

    /**
     * 创建刷新令牌
     * @param userId 用户ID
     * @param token 刷新令牌字符串
     * @param expiresAt 刷新令牌过期时间
     * @param issuedAt 刷新令牌签发时间
     * @return 操作是否成功
     */
    override fun createRefreshToken(userId: String, token: String, expiresAt: LocalDateTime, issuedAt: LocalDateTime): Boolean {
        return try {
            transaction {
                addLogger(StdOutSqlLogger)

                RefreshTokens.insert {
                    it[tokenId] = UUID.randomUUID().toString()
                    it[RefreshTokens.userId] = userId
                    it[RefreshTokens.token] = token
                    it[expiryDate] = expiresAt
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 查找刷新令牌
     * @param token 刷新令牌字符串
     * @return 找到的刷新令牌对象，若未找到则返回 null
     */
    override fun findRefreshToken(token: String): RefreshToken? {
        return transaction {
            addLogger(StdOutSqlLogger)
            RefreshTokens.selectAll().where { RefreshTokens.token eq token }
                .mapNotNull {
                    RefreshToken(
                        userId = it[RefreshTokens.userId],
                        token = it[RefreshTokens.token],
                        createdAt = it[RefreshTokens.issuedAt],
                        expiresAt = it[RefreshTokens.expiryDate]
                    )
                }.singleOrNull()
        }
    }

    /**
     * 删除刷新令牌
     * @param token 刷新令牌字符串
     * @return 操作是否成功
     */
    override fun deleteRefreshToken(token: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)

            RefreshTokens.deleteWhere { RefreshTokens.token eq token } > 0
        }
    }

    /**
     * 删除已过期的刷新令牌
     * @return 操作是否成功
     */
    override fun deleteExpiredTokens(): Boolean {
        val now = LocalDateTime.now()
        return transaction {
            addLogger(StdOutSqlLogger)

            RefreshTokens.deleteWhere { RefreshTokens.expiryDate lessEq now } > 0
        }
    }

    /**
     * 根据用户ID删除刷新令牌
     * @param userId 用户ID
     * @return 操作是否成功
     */
    override fun deleteRefreshTokensByUserId(userId: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            RefreshTokens.deleteWhere { RefreshTokens.userId eq userId } > 0
        }
    }

    /**
     * 生成刷新令牌
     * @param user 用户对象
     * @return 生成的刷新令牌字符串
     */
    override fun generateRefreshToken(user: User): String {
        val token = UUID.randomUUID().toString()
        transaction {
            RefreshTokens.insert {
                it[tokenId] = UUID.randomUUID().toString()
                it[userId] = user.userId
                it[RefreshTokens.token] = token
                it[issuedAt] = LocalDateTime.now()
                it[expiryDate] = LocalDateTime.now().plusDays(30)
            }
        }
        return token
    }

    /**
     * 验证刷新令牌是否有效
     * @param token 刷新令牌字符串
     * @return 刷新令牌是否有效
     */
    override fun validateRefreshToken(token: String): Boolean {
        return transaction {
            RefreshTokens.selectAll().where { RefreshTokens.token eq token }
                .firstOrNull()
                ?.let { it[RefreshTokens.expiryDate].isAfter(LocalDateTime.now()) } ?: false
        }
    }

    /**
     * 根据刷新令牌获取用户ID
     * @param token 刷新令牌字符串
     * @return 用户ID，若未找到则返回 null
     */
    override fun getUserIdFromToken(token: String): String? {
        return transaction {
            RefreshTokens.selectAll().where { RefreshTokens.token eq token }
                .firstOrNull()
                ?.get(RefreshTokens.userId)
        }
    }
}
