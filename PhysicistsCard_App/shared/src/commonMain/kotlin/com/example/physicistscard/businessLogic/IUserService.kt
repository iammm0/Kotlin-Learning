package com.example.physicistscard.businessLogic

import com.example.physicistscard.transmissionModels.auth.user.User

interface IUserService {
    suspend fun getUserInfo(targetUserId: String): Result<User?>
}