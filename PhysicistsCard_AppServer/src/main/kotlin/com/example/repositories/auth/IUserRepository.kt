package com.example.repositories.auth

import com.example.models.transmissionModels.auth.user.User

interface IUserRepository {
    fun createUser(user: User): User
    fun updateUserEmailOrPhone(userId: String, email: String?, phone: String?): Boolean
    fun findUserByEmailOrPhone(identifier: String): User?
    fun resetUserPassword(userId: String, newPasswordHash: String): Boolean
}

