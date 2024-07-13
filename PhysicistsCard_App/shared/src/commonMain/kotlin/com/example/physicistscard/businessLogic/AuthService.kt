package com.example.physicistscard.businessLogic


import com.example.physicistscard.apiServices.AuthApi
import com.example.physicistscard.transmissionModels.auth.requests.EmailCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.PasswordLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.PhoneCodeLoginRequest
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.ResetPasswordRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.ResetPasswordResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.*

/**
 * AuthService处理认证相关的业务逻辑
 * @param authApi 注入的AuthApi接口实现
 */
class AuthService(private val authApi: AuthApi) : IAuthService {

    /**
     * 注册用户
     * @param registrationRequest 注册请求对象
     * @return 注册响应对象
     */
    override suspend fun registerUser(registrationRequest: RegistrationRequest): HttpResponse {
        try {
            return authApi.register(registrationRequest)
        } catch (e: ClientRequestException) {
            throw Exception("注册失败: ${e.response.status}")
        }
    }

    /**
     * 发送验证码
     * @param sendCodeRequest 发送验证码请求对象
     * @return 发送验证码响应对象
     */
    override suspend fun sendVerificationCode(sendCodeRequest: SendCodeRequest): SendCodeResponse {
        return authApi.sendCode(sendCodeRequest)
    }

    /**
     * 使用密码登录
     * @param loginRequest 登录请求对象
     * @return 登录响应对象
     */
    override suspend fun loginWithPassword(loginRequest: PasswordLoginRequest): LoginResponse {
        return authApi.login(loginRequest)
    }

    /**
     * 使用邮箱验证码登录
     * @param loginRequest 邮箱验证码登录请求对象
     * @return 登录响应对象
     */
    override suspend fun emailCodeLogin(loginRequest: EmailCodeLoginRequest): LoginResponse {
        return authApi.emailCodeLogin(loginRequest)
    }

    /**
     * 使用手机验证码登录
     * @param loginRequest 手机验证码登录请求对象
     * @return 登录响应对象
     */
    override suspend fun phoneCodeLogin(loginRequest: PhoneCodeLoginRequest): LoginResponse {
        return authApi.phoneCodeLogin(loginRequest)
    }

    /**
     * 重置密码
     * @param resetPasswordRequest 重置密码请求对象
     * @return 重置密码响应对象
     */
    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): ResetPasswordResponse {
        try {
            return authApi.resetPassword(resetPasswordRequest)
        } catch (e: ClientRequestException) {
            throw Exception("重置密码失败: ${e.response.status}")
        }
    }
}
