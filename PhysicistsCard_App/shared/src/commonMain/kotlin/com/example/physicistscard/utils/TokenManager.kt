package com.example.physicistscard.utils

import com.example.physicistscard.apiServices.IAuthApiService

class TokenManager(private val authApiService: IAuthApiService) {
    private var token: String? = null
    private var refreshToken: String? = null

    // 保存token和refreshToken
    fun saveTokens(newToken: String, newRefreshToken: String) {
        token = newToken
        refreshToken = newRefreshToken
    }

    // 获取当前的token，如果不存在或者失效，则尝试刷新
    suspend fun getToken(): String? {
        if (token == null) {
            token = refreshToken?.let { refreshAccessToken(it) }
        }
        return token
    }

    // 刷新token
    private suspend fun refreshAccessToken(refreshToken: String): String? {
        val response = authApiService.refreshToken(refreshToken)
        if (response.success) {
            saveTokens(response.token ?: "", response.refreshToken ?: "")
            return response.token
        }
        return null
    }

    // 清除token（用于登出等场景）
    fun clearTokens() {
        token = null
        refreshToken = null
    }
}
