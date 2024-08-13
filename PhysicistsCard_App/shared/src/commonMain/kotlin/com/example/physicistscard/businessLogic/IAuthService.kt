package com.example.physicistscard.businessLogic

import com.example.physicistscard.transmissionModels.auth.MerchantApplication
import com.example.physicistscard.transmissionModels.auth.requests.AddAccountRequest
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.requests.SendCodeRequest
import com.example.physicistscard.transmissionModels.auth.requests.UserInfoUpdateRequest
import com.example.physicistscard.transmissionModels.auth.responses.LoginResponse
import com.example.physicistscard.transmissionModels.auth.responses.SendCodeResponse
import com.example.physicistscard.transmissionModels.auth.user.Role

/**
 * IAuthService - 认证系统的业务逻辑接口
 *
 * 该接口定义了与用户认证相关的业务逻辑方法。
 */
interface IAuthService {

    /**
     * 用户登录
     * @param email 用户的邮箱地址
     * @param password 用户的密码
     * @return 包含登录响应的结果，成功或失败
     */
    suspend fun loginUser(email: String, password: String): Result<LoginResponse>

    /**
     * 注册新用户
     * @param registrationRequest 注册请求对象，包含用户信息
     * @return 包含注册响应的结果，成功或失败
     */
    suspend fun registerUser(registrationRequest: RegistrationRequest): Result<LoginResponse>

    /**
     * 发送验证码到邮箱或手机
     * @param identifier 用户的标识符（邮箱或手机号）
     * @param request 发送验证码的请求对象
     * @return 包含验证码发送结果的结果，成功或失败
     */
    suspend fun sendVerificationCode(identifier: String, request: SendCodeRequest): Result<SendCodeResponse>

    /**
     * 重置用户密码
     * @param identifier 用户的标识符（邮箱或手机号）
     * @param newPassword 新密码
     * @param code 验证码
     * @return 密码重置结果，成功或失败
     */
    suspend fun resetPassword(identifier: String, newPassword: String, code: String): Result<Boolean>

    /**
     * 刷新JWT令牌
     * @param refreshToken 用户的刷新令牌
     * @return 包含新令牌的登录响应，成功或失败
     */
    suspend fun refreshToken(refreshToken: String): Result<LoginResponse>

    /**
     * 用户登出
     * @param userId 用户ID
     * @return 登出结果，成功或失败
     */
    suspend fun logout(userId: String): Result<Boolean>

    /**
     * 申请商家账号
     * @param userId 用户ID
     * @param application 商家申请对象
     * @return 商家申请结果，成功或失败
     */
    suspend fun applyForMerchant(userId: String, application: MerchantApplication): Result<Boolean>

    /**
     * 审批商家申请
     * @param userId 用户ID
     * @return 审批结果，成功或失败
     */
    suspend fun approveMerchantApplication(userId: String): Result<Boolean>

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param request 更新用户信息的请求对象
     * @return 更新结果，成功或失败
     */
    suspend fun updateUserInfo(userId: String, request: UserInfoUpdateRequest): Result<Boolean>

    /**
     * 绑定新的手机号
     * @param userId 用户ID
     * @param newPhone 新手机号
     * @param oldPhoneCode 原手机号的验证码
     * @return 绑定结果，成功或失败
     */
    suspend fun bindPhone(userId: String, newPhone: String, oldPhoneCode: String): Result<Boolean>

    /**
     * 绑定新的邮箱
     * @param userId 用户ID
     * @param newEmail 新邮箱地址
     * @param oldEmailCode 原邮箱的验证码
     * @return 绑定结果，成功或失败
     */
    suspend fun bindEmail(userId: String, newEmail: String, oldEmailCode: String): Result<Boolean>

    /**
     * 添加新账户（仅限管理员）
     * @param request 添加账户的请求对象
     * @param currentUserRole 当前用户的角色
     * @return 添加结果，成功或失败
     */
    suspend fun addAccount(request: AddAccountRequest, currentUserRole: Role): Result<Boolean>
}
