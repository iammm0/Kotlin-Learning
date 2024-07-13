package com.example.api.impl

import com.example.physicistscard.apiServices.AuthApi
import com.example.physicistscard.transmissionModels.auth.requests.AddAccountRequest
import com.example.physicistscard.transmissionModels.auth.requests.EmailCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.PasswordLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.PhoneCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.ResetPasswordRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.ResetPasswordResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * 认证API实现类，负责实现认证相关操作
 *
 * @property client HttpClient实例
 * @property baseUrl 基础URL
 */
class AuthApiImpl(private val client: HttpClient, private val baseUrl: String) : AuthApi {

    override suspend fun register(registrationRequest: RegistrationRequest): HttpResponse {
        return client.post("$baseUrl/user/register") {
            contentType(ContentType.Application.Json)
            setBody(registrationRequest)
        }
    }

    override suspend fun emailCodeLogin(loginRequest: EmailCodeLoginRequest): LoginResponse {
        return client.post("$baseUrl/user/email-code-login") {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }

    override suspend fun phoneCodeLogin(loginRequest: PhoneCodeLoginRequest): LoginResponse {
        return client.post("$baseUrl/user/phone-code-login") {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }

    override suspend fun sendCode(sendCodeRequest: SendCodeRequest): SendCodeResponse {
        return client.post("$baseUrl/user/send-code") {
            contentType(ContentType.Application.Json)
            setBody(sendCodeRequest)
        }.body()
    }

    override suspend fun login(loginRequest: PasswordLoginRequest): LoginResponse {
        return client.post("$baseUrl/user/login") {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): ResetPasswordResponse {
        return client.post("$baseUrl/user/password/change-password") {
            contentType(ContentType.Application.Json)
            setBody(resetPasswordRequest)
        }.body()
    }

    override suspend fun becomeSeller(token: String): HttpResponse {
        return client.post("$baseUrl/user/become_seller") {
            bearerAuth(token)
        }
    }

    override suspend fun logout(token: String): HttpResponse {
        return client.post("$baseUrl/user/logout") {
            bearerAuth(token)
        }
    }

    override suspend fun changeAvatar(token: String, avatarUrl: String): HttpResponse {
        return client.post("$baseUrl/user/change-avatar") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(avatarUrl)
        }
    }

    override suspend fun bindPhone(token: String, phone: String): HttpResponse {
        return client.post("$baseUrl/user/binding-phone") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(phone)
        }
    }

    override suspend fun bindEmail(token: String, email: String): HttpResponse {
        return client.post("$baseUrl/user/binding-email") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(email)
        }
    }

    override suspend fun addAccount(token: String, addAccountRequest: AddAccountRequest): HttpResponse {
        return client.post("$baseUrl/user/add-account") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(addAccountRequest)
        }
    }
}

