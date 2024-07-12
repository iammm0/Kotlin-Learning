package com.example.services.dataAccessServices.auth

import com.example.models.transmissionModels.auth.user.User

interface ITokenService {
    /**
     * 为指定用户生成新的认证令牌
     *
     * @param user 用户对象
     * @return 生成的令牌
     */
    fun generateToken(user: User): String

    /**
     * 验证给定的令牌的有效性
     *
     * @param token 令牌
     * @return 令牌是否有效
     */
    fun verifyToken(token: String): Boolean

    /**
     * 使用旧令牌生成一个新令牌
     *
     * @param token 旧令牌
     * @return 新令牌或null（如果刷新失败）
     */
    fun refreshToken(token: String): String?
}
