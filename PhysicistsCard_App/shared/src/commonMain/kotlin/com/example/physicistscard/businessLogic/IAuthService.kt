package com.example.physicistscard.businessLogic

import com.example.physicistscard.transmissionModels.auth.requests.*
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.ResetPasswordResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse
import io.ktor.client.statement.HttpResponse

/**
 * IAuthService定义了认证相关的业务逻辑接口
 */
interface IAuthService {
    /**
     * 注册用户
     * @param registrationRequest 注册请求对象
     * @return 注册响应对象
     */
    suspend fun registerUser(registrationRequest: RegistrationRequest): HttpResponse

    /**
     * 发送验证码
     * @param sendCodeRequest 发送验证码请求对象
     * @return 发送验证码响应对象
     */
    suspend fun sendVerificationCode(sendCodeRequest: SendCodeRequest): SendCodeResponse

    /**
     * 使用密码登录
     * @param loginRequest 登录请求对象
     * @return 登录响应对象
     */
    suspend fun loginWithPassword(loginRequest: PasswordLoginRequest): LoginResponse

    /**
     * 使用邮箱验证码登录
     * @param loginRequest 邮箱验证码登录请求对象
     * @return 登录响应对象
     */
    suspend fun emailCodeLogin(loginRequest: EmailCodeLoginRequest): LoginResponse

    /**
     * 使用手机验证码登录
     * @param loginRequest 手机验证码登录请求对象
     * @return 登录响应对象
     */
    suspend fun phoneCodeLogin(loginRequest: PhoneCodeLoginRequest): LoginResponse

    /**
     * 重置密码
     * @param resetPasswordRequest 重置密码请求对象
     * @return 重置密码响应对象
     */
    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): ResetPasswordResponse
}
