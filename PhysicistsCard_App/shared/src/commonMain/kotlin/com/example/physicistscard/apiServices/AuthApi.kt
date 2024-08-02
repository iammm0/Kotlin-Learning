package com.example.physicistscard.apiServices

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
import io.ktor.client.statement.*

/**
 * 认证相关接口，定义了各种用户操作的API
 */
interface AuthApi {

    /**
     * 注册新用户
     *
     * @param registrationRequest 注册请求数据
     * @return 注册结果的HTTP响应
     */
    suspend fun register(registrationRequest: RegistrationRequest): HttpResponse

    /**
     * 使用邮箱验证码登录
     *
     * @param loginRequest 邮箱验证码登录请求数据
     * @return 登录结果
     */
    suspend fun emailCodeLogin(loginRequest: EmailCodeLoginRequest): LoginResponse

    /**
     * 使用手机验证码登录
     *
     * @param loginRequest 手机验证码登录请求数据
     * @return 登录结果
     */
    suspend fun phoneCodeLogin(loginRequest: PhoneCodeLoginRequest): LoginResponse

    /**
     * 发送验证码
     *
     * @param sendCodeRequest 发送验证码请求数据
     * @return 发送验证码结果
     */
    suspend fun sendCode(sendCodeRequest: SendCodeRequest): SendCodeResponse

    /**
     * 使用邮箱或手机号及密码登录
     *
     * @param loginRequest 登录请求数据
     * @return 登录结果
     */
    suspend fun login(loginRequest: PasswordLoginRequest): LoginResponse

    /**
     * 重置密码
     *
     * @param resetPasswordRequest 重置密码请求数据
     * @return 重置密码结果
     */
    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): ResetPasswordResponse

    /**
     * 注册为商家
     *
     * @param token 用户认证令牌
     * @return 注册为商家的HTTP响应
     */
    suspend fun becomeSeller(token: String): HttpResponse

    /**
     * 用户登出
     *
     * @param token 用户认证令牌
     * @return 登出结果的HTTP响应
     */
    suspend fun logout(token: String): HttpResponse

    /**
     * 更换头像
     *
     * @param token 用户认证令牌
     * @param avatarUrl 新的头像URL
     * @return 更换头像结果的HTTP响应
     */
    suspend fun changeAvatar(token: String, avatarUrl: String): HttpResponse

    /**
     * 绑定手机
     *
     * @param token 用户认证令牌
     * @param phone 新的手机号
     * @return 绑定手机结果的HTTP响应
     */
    suspend fun bindPhone(token: String, phone: String): HttpResponse

    /**
     * 绑定邮箱
     *
     * @param token 用户认证令牌
     * @param email 新的邮箱地址
     * @return 绑定邮箱结果的HTTP响应
     */
    suspend fun bindEmail(token: String, email: String): HttpResponse

    /**
     * 添加账户
     *
     * @param token 用户认证令牌
     * @param addAccountRequest 添加账户请求数据
     * @return 添加账户结果的HTTP响应
     */
    suspend fun addAccount(token: String, addAccountRequest: AddAccountRequest): HttpResponse
}

