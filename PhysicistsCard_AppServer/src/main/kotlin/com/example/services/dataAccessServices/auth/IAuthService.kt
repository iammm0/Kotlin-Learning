package com.example.services.dataAccessServices.auth

import com.example.models.transmissionModels.auth.requests.RegistrationRequest
import com.example.models.transmissionModels.auth.requests.SendCodeRequest
import com.example.models.transmissionModels.auth.responses.RegistrationResponse
import com.example.models.transmissionModels.auth.responses.SendCodeResponse


interface IAuthService {
    fun verifyCode(identifier: String, code: String): Boolean
    fun registerUser(registrationRequest: RegistrationRequest): RegistrationResponse?
    fun sendVerificationCode(identifier: String, transData: SendCodeRequest): SendCodeResponse
    fun loginWithPassword(identifier: String, password: String): Boolean
    fun loginWithVerificationCode(identifier: String, code: String): Boolean
    fun resetPassword(identifier: String, newPassword: String): Boolean
}
