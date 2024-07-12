package com.example.services.dataAccessServices.auth

import com.example.models.transmissionModels.auth.user.User

// 负责生成、验证和刷新这些令牌
interface TokenService {
    fun generateToken(user: User): String
    // 为指定用户生成新的认证令牌，通常在登录成功后调用。

    fun verifyToken(token: String): Boolean
    // 验证给定的令牌的有效性，用于请求认证。

    fun refreshToken(token: String): String?
    // 使用旧令牌来生成一个新令牌，用于令牌续期。
}