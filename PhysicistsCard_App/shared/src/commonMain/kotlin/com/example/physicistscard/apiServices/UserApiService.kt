package com.example.physicistscard.apiServices

import com.example.physicistscard.transmissionModels.auth.user.User
import com.example.physicistscard.utils.TokenManager
import com.example.physicistscard.utils.safeBodyOrNull
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class UserApiService(private val client: HttpClient) : IUserApiService {

    override suspend fun getUserInfo(targetUserId: String, tokenManager: TokenManager): User? {
        val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
        return client.get {
            url("${NetworkConfig.BASE_URL}/user/$targetUserId")
            header(HttpHeaders.Authorization, "Bearer $token")
        }.safeBodyOrNull()
    }
}