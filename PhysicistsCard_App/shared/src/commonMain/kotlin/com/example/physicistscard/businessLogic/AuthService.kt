package com.example.physicistscard.businessLogic

import com.example.physicistscard.apiServices.IAuthApiService
import com.example.physicistscard.transmissionModels.auth.MerchantApplication
import com.example.physicistscard.transmissionModels.auth.requests.AddAccountRequest
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.requests.UserInfoUpdateRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse
import com.example.physicistscard.transmissionModels.auth.user.Role
import com.example.physicistscard.utils.TokenManager

class AuthService(
    private val authApiService: IAuthApiService,
    private val tokenManager: TokenManager
) : IAuthService {

    override suspend fun loginUser(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = authApiService.loginWithPassword(email, password)
            if (response.success) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerUser(registrationRequest: RegistrationRequest): Result<LoginResponse> {
        return try {
            val response = authApiService.registerUser(registrationRequest)
            if (response.success) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendVerificationCode(identifier: String, request: SendCodeRequest): Result<SendCodeResponse> {
        return try {
            val response = authApiService.sendVerificationCode(identifier, request)
            if (response.success) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun resetPassword(identifier: String, newPassword: String, code: String): Result<Boolean> {
        return try {
            val success = authApiService.resetPassword(identifier, newPassword, code)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<LoginResponse> {
        return try {
            val response = authApiService.refreshToken(refreshToken)

            if (response.success) {
                // 成功刷新token，保存新令牌
                tokenManager.saveTokens(response.token ?: "", response.refreshToken ?: "")
                Result.success(response)
            } else {
                // 如果刷新失败，返回失败结果
                Result.failure(Exception("刷新令牌失败: ${response.errorMessage}"))
            }
        } catch (e: Exception) {
            // 捕获异常并返回失败结果
            Result.failure(e)
        }
    }

    override suspend fun logout(userId: String): Result<Boolean> {
        return try {
            val success = authApiService.logout(userId, tokenManager)
            if (success) {
                tokenManager.clearTokens()
            }
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun applyForMerchant(userId: String, application: MerchantApplication): Result<Boolean> {
        return try {
            val success = authApiService.applyForMerchant(userId, application, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun approveMerchantApplication(userId: String): Result<Boolean> {
        return try {
            val success = authApiService.approveMerchantApplication(userId, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserInfo(userId: String, request: UserInfoUpdateRequest): Result<Boolean> {
        return try {
            val success = authApiService.updateUserInfo(userId, request, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun bindPhone(userId: String, newPhone: String, oldPhoneCode: String): Result<Boolean> {
        return try {
            val success = authApiService.bindPhone(userId, newPhone, oldPhoneCode, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun bindEmail(userId: String, newEmail: String, oldEmailCode: String): Result<Boolean> {
        return try {
            val success = authApiService.bindEmail(userId, newEmail, oldEmailCode, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addAccount(request: AddAccountRequest, currentUserRole: Role): Result<Boolean> {
        return try {
            val success = authApiService.addAccount(request, currentUserRole, tokenManager)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
