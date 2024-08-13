import com.example.physicistscard.apiServices.IAuthApiService
import com.example.physicistscard.apiServices.NetworkConfig
import com.example.physicistscard.transmissionModels.auth.MerchantApplication
import com.example.physicistscard.transmissionModels.auth.requests.AddAccountRequest
import com.example.physicistscard.transmissionModels.auth.requests.BindEmailRequest
import com.example.physicistscard.transmissionModels.auth.requests.BindPhoneRequest
import com.example.physicistscard.transmissionModels.auth.requests.EmailCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.PasswordLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.RefreshTokenRequest
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.ResetPasswordRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.requests.UserInfoUpdateRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse
import com.example.physicistscard.transmissionModels.auth.user.Role
import com.example.physicistscard.utils.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType


class AuthApiService(private val client: HttpClient) : IAuthApiService {

    override suspend fun registerUser(registrationRequest: RegistrationRequest): LoginResponse {
        return try {
            client.post {
                url("${NetworkConfig.BASE_URL}/user/register")
                contentType(ContentType.Application.Json)
                setBody(registrationRequest)
            }.body()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun loginWithVerificationCode(email: String, code: String): LoginResponse {
        return try {
            client.post {
                url("${NetworkConfig.BASE_URL}/user/email-code-login")
                contentType(ContentType.Application.Json)
                setBody(EmailCodeLoginRequest(email, code))
            }.body()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun loginWithPassword(identifier: String, password: String): LoginResponse {
        return try {
            client.post {
                url("${NetworkConfig.BASE_URL}/user/login")
                contentType(ContentType.Application.Json)
                setBody(PasswordLoginRequest(identifier, password))
            }.body()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun sendVerificationCode(identifier: String, request: SendCodeRequest): SendCodeResponse {
        return try {
            client.post {
                url("${NetworkConfig.BASE_URL}/user/send-code")
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        } catch (e: Exception) {
            handleSendCodeException(e)
        }
    }

    override suspend fun resetPassword(identifier: String, newPassword: String, code: String): Boolean {
        return try {
            client.post {
                url("${NetworkConfig.BASE_URL}/user/password/change-password")
                contentType(ContentType.Application.Json)
                setBody(ResetPasswordRequest(identifier, newPassword, code))
            }.body()
        } catch (e: Exception) {
            handleException(e, false)
        }
    }

    override suspend fun refreshToken(refreshToken: String): LoginResponse {
        return try {
            client.post {
                url("${NetworkConfig.BASE_URL}/user/refresh-token")
                contentType(ContentType.Application.Json)
                setBody(RefreshTokenRequest(refreshToken))
            }.body()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun logout(userId: String, tokenManager: TokenManager): Boolean {
        return try {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            client.post {
                url("${NetworkConfig.BASE_URL}/user/logout")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token") // 设置 Authorization 请求头
            }.status == HttpStatusCode.OK
        } catch (e: Exception) {
            handleException(e, false)
        }
    }

    override suspend fun applyForMerchant(userId: String, application: MerchantApplication, tokenManager: TokenManager): Boolean {
        return try {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            client.post {
                url("${NetworkConfig.BASE_URL}/user/apply-for-merchant")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token") // 设置 Authorization 请求头
                setBody(application)
            }.status == HttpStatusCode.OK
        } catch (e: Exception) {
            handleException(e, false)
        }
    }

    override suspend fun approveMerchantApplication(userId: String, tokenManager: TokenManager): Boolean {
        return try {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            client.post {
                url("${NetworkConfig.BASE_URL}/user/approve-merchant")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token") // 设置 Authorization 请求头
                setBody(userId)
            }.status == HttpStatusCode.OK
        } catch (e: Exception) {
            handleException(e, false)
        }
    }

    override suspend fun updateUserInfo(userId: String, request: UserInfoUpdateRequest, tokenManager: TokenManager): Boolean {
        return try {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            client.post {
                url("${NetworkConfig.BASE_URL}/user/update-user-info")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token") // 设置 Authorization 请求头
                setBody(request)
            }.status == HttpStatusCode.OK
        } catch (e: Exception) {
            handleException(e, false)
        }
    }

    override suspend fun bindPhone(userId: String, newPhone: String, oldPhoneCode: String, tokenManager: TokenManager): Boolean {
        return try {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            client.post {
                url("${NetworkConfig.BASE_URL}/user/binding-phone")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token") // 设置 Authorization 请求头
                setBody(BindPhoneRequest(newPhone, oldPhoneCode))
            }.status == HttpStatusCode.OK
        } catch (e: Exception) {
            handleException(e, false)
        }
    }

    override suspend fun bindEmail(userId: String, newEmail: String, oldEmailCode: String, tokenManager: TokenManager): Boolean {
        return try {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            client.post {
                url("${NetworkConfig.BASE_URL}/user/binding-email")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token") // 设置 Authorization 请求头
                setBody(BindEmailRequest(newEmail, oldEmailCode))
            }.status == HttpStatusCode.OK
        } catch (e: Exception) {
            handleException(e, false)
        }
    }

    override suspend fun addAccount(request: AddAccountRequest, currentUserRole: Role, tokenManager: TokenManager): Boolean {
        return try {
            val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
            client.post {
                url("${NetworkConfig.BASE_URL}/user/add-account")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token") // 设置 Authorization 请求头
                setBody(request)
            }.status == HttpStatusCode.OK
        } catch (e: Exception) {
            handleException(e, false)
        }
    }

    /**
     * 处理异常并返回默认值
     */
    private fun <T> handleException(e: Exception, defaultValue: T): T {
        when (e) {
            is ClientRequestException -> {
                // 4xx 响应异常
                "Client error: ${e.response.status}"
            }
            is ServerResponseException -> {
                // 5xx 响应异常
                "Server error: ${e.response.status}"
            }
            else -> {
                // 其他未知异常
                "Unexpected error: ${e.message ?: "Unknown error occurred"}"
            }
        }
        return defaultValue
    }

    private fun handleSendCodeException(e: Exception): SendCodeResponse {
        return when (e) {
            is ClientRequestException -> {
                // 处理4xx错误
                SendCodeResponse(success = false, message = "Client error: ${e.response.status.description}", errorCode = e.response.status.value.toString())
            }
            is ServerResponseException -> {
                // 处理5xx错误
                SendCodeResponse(success = false, message = "Server error: ${e.response.status.description}", errorCode = e.response.status.value.toString())
            }
            else -> {
                // 处理网络错误和其他未知错误
                SendCodeResponse(success = false, message = "Unexpected error: ${e.message}", errorCode = "UNKNOWN_ERROR")
            }
        }
    }

    /**
     * 处理异常并返回一个空的 `LoginResponse`
     */
    private fun handleException(e: Exception): LoginResponse {
        return handleException(e, LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "An error occurred"))
    }
}
