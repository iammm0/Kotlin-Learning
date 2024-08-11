package com.example.services.dataAccessServices.auth

import com.example.models.transmissionModels.auth.merchant.MerchantApplication
import com.example.models.transmissionModels.auth.requests.*
import com.example.models.transmissionModels.auth.responses.*
import com.example.models.transmissionModels.auth.user.Role

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
     * 执行用户登出操作。
     * 登出操作会使用户的所有刷新令牌失效，用户将无法再使用这些令牌来获取新的访问令牌。
     *
     * @param userId 用户的唯一标识符。
     * @return 如果成功登出则返回 true，登出失败则返回 false。
     * @throws IllegalArgumentException 如果提供的用户ID无效或用户不存在时抛出。
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
     * 绑定用户手机号码。
     * 当用户尝试绑定新的手机号码时，如果用户已经绑定了旧的手机号码，则需要提供旧手机号码的验证码进行验证。
     *
     * @param userId 用户的唯一标识符。
     * @param newPhone 用户想要绑定的新手机号码。
     * @param oldPhoneVerificationCode 如果用户已经绑定了旧手机号码，则需要提供旧手机号码的验证码进行验证。否则为 null。
     * @return 如果成功绑定新手机号码则返回 true，绑定失败则返回 false。
     * @throws IllegalArgumentException 当新手机号码已被其他用户绑定，或者提供的旧手机号码验证码无效时抛出。
     */
    fun bindPhone(userId: String, newPhone: String, oldPhoneVerificationCode: String?): Boolean

    /**
     * 绑定用户邮箱地址。
     * 当用户尝试绑定新的邮箱地址时，如果用户已经绑定了旧的邮箱地址，则需要提供旧邮箱地址的验证码进行验证。
     *
     * @param userId 用户的唯一标识符。
     * @param newEmail 用户想要绑定的新邮箱地址。
     * @param oldEmailVerificationCode 如果用户已经绑定了旧邮箱地址，则需要提供旧邮箱地址的验证码进行验证。否则为 null。
     * @return 如果成功绑定新邮箱地址则返回 true，绑定失败则返回 false。
     * @throws IllegalArgumentException 当新邮箱地址已被其他用户绑定，或者提供的旧邮箱验证码无效时抛出。
     */
    fun bindEmail(userId: String, newEmail: String, oldEmailVerificationCode: String?): Boolean


    /**
     * 添加新用户账户。
     * 该方法仅允许具有超级管理员角色的用户执行，用于在系统中添加新用户。
     *
     * @param request 包含新账户信息的请求对象，包含用户名、邮箱、手机号、密码和角色。
     * @param currentUserRole 当前执行此操作用户的角色。仅当角色为 SUPER_ADMIN 时，允许添加新账户。
     * @return 添加账户是否成功。如果成功添加账户返回 true，失败返回 false。
     * @throws IllegalArgumentException 如果当前用户没有权限添加账户，或者新账户的邮箱或手机号已被使用。
     */
    fun addAccount(request: AddAccountRequest, currentUserRole: Role): Boolean

    /**
     * 使用刷新令牌获取新的访问令牌
     * @param refreshToken 刷新令牌
     * @return 登录响应对象，包含新的访问令牌和刷新令牌
     */
    fun refreshToken(refreshToken: String): LoginResponse

    /**
     * 用户提交商家入驻申请。
     *
     * @param userId 用户的唯一标识符。
     * @param application 商家申请对象，包含用户提交的商家信息。
     * @return 如果申请提交成功则返回 true，提交失败则返回 false。
     */
    fun applyForMerchant(userId: String, application: MerchantApplication): Boolean

    /**
     * 审核并批准商家申请。
     *
     * @param userId 用户的唯一标识符。
     * @return 如果申请审核通过并更新用户角色为商家则返回 true，失败则返回 false。
     */
    fun approveMerchantApplication(userId: String): Boolean

    /**
     * 更新用户的个人信息。
     *
     * @param userId 用户的唯一标识符。
     * @param updateRequest 包含要更新的用户信息。
     * @return 如果更新成功则返回 true，更新失败则返回 false。
     * @throws IllegalArgumentException 如果更新的参数无效或冲突。
     */
    fun updateUserInfo(userId: String, updateRequest: UserInfoUpdateRequest): Boolean
}
