package com.example.services.dataAccessServices.auth

import com.example.models.transmissionModels.auth.requests.*
import com.example.models.transmissionModels.auth.responses.*
import com.example.models.transmissionModels.auth.user.User
import com.example.models.transmissionModels.auth.verificationCodes.VerificationType

interface IAuthService {
    /**
     * 注册新用户
     * @param registrationRequest 包含用户注册信息的请求对象
     * @return 注册响应对象，包含注册成功与否的信息
     */
    fun registerUser(registrationRequest: RegistrationRequest): RegistrationResponse

    /**
     * 发送验证码到用户的邮箱或手机
     * @param identifier 用户的邮箱或手机
     * @param transData 包含验证码操作类型的请求对象
     * @return 发送验证码的响应对象，包含发送成功与否的信息
     */
    fun sendVerificationCode(identifier: String, transData: SendCodeRequest): SendCodeResponse

    /**
     * 使用密码登录
     * @param identifier 用户的邮箱或手机
     * @param password 用户的密码
     * @return 登录响应对象，包含登录成功与否的信息和JWT令牌
     */
    fun loginWithPassword(identifier: String, password: String): LoginResponse

    /**
     * 使用验证码登录
     * @param identifier 用户的邮箱或手机
     * @param code 验证码
     * @return 登录响应对象，包含登录成功与否的信息和JWT令牌
     */
    fun loginWithVerificationCode(identifier: String, code: String): LoginResponse

    /**
     * 验证验证码的有效性
     * @param identifier 用户的邮箱或手机
     * @param code 验证码
     * @return 验证码是否有效
     */
    fun verifyCode(identifier: String, code: String): Boolean

    /**
     * 重置用户密码
     * @param identifier 用户的邮箱或手机
     * @param newPassword 新密码
     * @return 重置密码是否成功
     */
    fun resetPassword(identifier: String, newPassword: String): Boolean

    /**
     * 注册为商家
     * @param userId 用户ID
     * @return 注册为商家是否成功
     */
    fun becomeSeller(userId: String): Boolean

    /**
     * 用户登出
     * @param userId 用户ID
     * @return 登出是否成功
     */
    fun logout(userId: String): Boolean

    /**
     * 切换用户头像
     * @param userId 用户ID
     * @param avatarUrl 新的头像URL
     * @return 切换头像是否成功
     */
    fun changeAvatar(userId: String, avatarUrl: String): Boolean

    /**
     * 绑定用户手机
     * @param userId 用户ID
     * @param phone 新的手机号码
     * @return 绑定手机是否成功
     */
    fun bindPhone(userId: String, phone: String): Boolean

    /**
     * 绑定用户邮箱
     * @param userId 用户ID
     * @param email 新的邮箱地址
     * @return 绑定邮箱是否成功
     */
    fun bindEmail(userId: String, email: String): Boolean

    /**
     * 添加新的账户
     * @param request 包含新账户信息的请求对象
     * @return 添加账户是否成功
     */
    fun addAccount(request: AddAccountRequest): Boolean

    /**
     * 使用刷新令牌获取新的访问令牌
     * @param refreshToken 刷新令牌
     * @return 登录响应对象，包含新的访问令牌和刷新令牌
     */
    fun refreshToken(refreshToken: String): LoginResponse
}
