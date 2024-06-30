package com.example.physicistscard.apiServices

import com.example.physicistscard.transmissionModels.auth.requests.EmailCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.PhoneCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.ResetPasswordRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.RegistrationResponse
import com.example.physicistscard.transmissionModels.auth.responses.ResetPasswordResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse

interface AuthApiService {
    suspend fun registerUser(request: RegistrationRequest): RegistrationResponse
    suspend fun loginWithEmailCode(request: EmailCodeLoginRequest): LoginResponse
    suspend fun loginWithPhoneCode(request: PhoneCodeLoginRequest): LoginResponse
    suspend fun sendVerificationCode(request: SendCodeRequest): SendCodeResponse
    suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse
}
