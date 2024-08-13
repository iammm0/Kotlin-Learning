package com.example.physicistscard.apiServices

import com.example.physicistscard.transmissionModels.auth.MerchantApplication
import com.example.physicistscard.transmissionModels.auth.requests.AddAccountRequest
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.requests.UserInfoUpdateRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse
import com.example.physicistscard.transmissionModels.auth.user.Role
import com.example.physicistscard.utils.TokenManager

/**
 * IAuthService - 认证系统的服务接口
 */
interface IAuthApiService {

    /**
     * 使用邮箱或手机注册账户
     * @param registrationRequest 注册请求，包含用户信息
     * @return 注册结果的响应对象
     */
    suspend fun registerUser(registrationRequest: RegistrationRequest): LoginResponse

    /**
     * 使用邮箱验证码登录
     * @param email 邮箱地址
     * @param code 验证码
     * @return 登录结果的响应对象
     */
    suspend fun loginWithVerificationCode(email: String, code: String): LoginResponse

    /**
     * 使用密码登录
     * @param identifier 用户标识符（邮箱或手机号）
     * @param password 密码
     * @return 登录结果的响应对象
     */
    suspend fun loginWithPassword(identifier: String, password: String): LoginResponse

    /**
     * 发送验证码到邮箱或手机
     * @param identifier 用户标识符（邮箱或手机号）
     * @param request 发送验证码的请求对象
     * @return 发送结果的响应对象
     */
    suspend fun sendVerificationCode(identifier: String, request: SendCodeRequest): SendCodeResponse

    /**
     * 重置密码
     * @param identifier 用户标识符（邮箱或手机号）
     * @param newPassword 新密码
     * @return 密码重置结果的响应对象
     */
    suspend fun resetPassword(identifier: String, newPassword: String, code: String): Boolean

    /**
     * 刷新令牌
     * @param refreshToken 刷新令牌
     * @return 新的访问令牌和刷新令牌
     */
    suspend fun refreshToken(refreshToken: String): LoginResponse

    /**
     * 登出用户
     * @param userId 用户ID
     * @return 登出结果是否成功
     */
    suspend fun logout(userId: String, tokenManager: TokenManager): Boolean

    /**
     * 申请商家账号
     * @param userId 用户ID
     * @param application 商家申请对象
     * @return 申请结果是否成功
     */
    suspend fun applyForMerchant(userId: String, application: MerchantApplication, tokenManager: TokenManager): Boolean

    /**
     * 审批商家申请
     * @param userId 用户ID
     * @return 审批结果是否成功
     */
    suspend fun approveMerchantApplication(userId: String, tokenManager: TokenManager): Boolean

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param request 更新用户信息的请求对象
     * @return 更新结果是否成功
     */
    suspend fun updateUserInfo(userId: String, request: UserInfoUpdateRequest, tokenManager: TokenManager): Boolean

    /**
     * 绑定新的手机号
     * @param userId 用户ID
     * @param newPhone 新手机号
     * @param oldPhoneCode 原手机号的验证码
     * @return 绑定结果是否成功
     */
    suspend fun bindPhone(userId: String, newPhone: String, oldPhoneCode: String, tokenManager: TokenManager): Boolean

    /**
     * 绑定新的邮箱
     * @param userId 用户ID
     * @param newEmail 新邮箱地址
     * @param oldEmailCode 原邮箱的验证码
     * @return 绑定结果是否成功
     */
    suspend fun bindEmail(userId: String, newEmail: String, oldEmailCode: String, tokenManager: TokenManager): Boolean

    /**
     * 添加新账户（仅限管理员）
     * @param request 添加账户的请求对象
     * @param currentUserRole 当前用户的角色
     * @return 添加结果是否成功
     */
    suspend fun addAccount(request: AddAccountRequest, currentUserRole: Role, tokenManager: TokenManager): Boolean
}