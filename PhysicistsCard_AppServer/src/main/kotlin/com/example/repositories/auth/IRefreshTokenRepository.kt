package com.example.repositories.auth

import com.example.models.transmissionModels.auth.tokens.RefreshToken
import com.example.models.transmissionModels.auth.user.User
import java.time.LocalDateTime

interface IRefreshTokenRepository {
    /**
     * 创建刷新令牌
     * @param userId 用户ID
     * @param token 刷新令牌字符串
     * @param expiresAt 刷新令牌过期时间
     * @param issuedAt 刷新令牌签发时间
     * @return 操作是否成功
     */
    fun createRefreshToken(userId: String, token: String, expiresAt: LocalDateTime, issuedAt: LocalDateTime): Boolean

    /**
     * 查找刷新令牌
     * @param token 刷新令牌字符串
     * @return 找到的刷新令牌对象，若未找到则返回 null
     */
    fun findRefreshToken(token: String): RefreshToken?

    /**
     * 删除刷新令牌
     * @param token 刷新令牌字符串
     * @return 操作是否成功
     */
    fun deleteRefreshToken(token: String): Boolean

    /**
     * 删除已过期的刷新令牌
     * @return 操作是否成功
     */
    fun deleteExpiredTokens(): Boolean

    /**
     * 根据用户ID删除刷新令牌
     * @param userId 用户ID
     * @return 操作是否成功
     */
    fun deleteRefreshTokensByUserId(userId: String): Boolean

    /**
     * 生成刷新令牌
     * @param user 用户对象
     * @return 生成的刷新令牌字符串
     */
    fun generateRefreshToken(user: User): String

    /**
     * 验证刷新令牌是否有效
     * @param token 刷新令牌字符串
     * @return 刷新令牌是否有效
     */
    fun validateRefreshToken(token: String): Boolean

    /**
     * 根据刷新令牌获取用户ID
     * @param token 刷新令牌字符串
     * @return 用户ID，若未找到则返回 null
     */
    fun getUserIdFromToken(token: String): String?
}
