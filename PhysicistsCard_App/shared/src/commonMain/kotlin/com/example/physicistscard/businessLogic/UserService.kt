package com.example.physicistscard.businessLogic

import com.example.physicistscard.apiServices.IUserApiService
import com.example.physicistscard.transmissionModels.auth.user.User
import com.example.physicistscard.utils.TokenManager

class UserService(
    private val userApiService: IUserApiService,
    private val tokenManager: TokenManager
) : IUserService {

    override suspend fun getUserInfo(targetUserId: String): Result<User?> {
        return try {
            val user = userApiService.getUserInfo(targetUserId, tokenManager)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
