package com.example.services.dataAccessServices.auth

import IUserRepository
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.models.transmissionModels.auth.merchant.ApplicationStatus
import com.example.models.transmissionModels.auth.merchant.MerchantApplication
import com.example.models.transmissionModels.auth.requests.*
import com.example.models.transmissionModels.auth.user.User
import com.example.models.transmissionModels.auth.verificationCodes.VerificationType
import com.example.models.transmissionModels.auth.responses.LoginResponse
import com.example.models.transmissionModels.auth.responses.RegistrationResponse
import com.example.models.transmissionModels.auth.responses.SendCodeResponse
import com.example.models.transmissionModels.auth.user.Role
import com.example.repositories.auth.*
import com.example.services.thirdPartyProviderServices.AliyunEmailService
import com.example.services.thirdPartyProviderServices.AliyunSmsService
import com.example.utils.hashPassword
import java.time.LocalDateTime
import java.util.*

class AuthService(
    private val tokenService: ITokenService, // 注入TokenService
    private val userRepository: IUserRepository,
    private val verificationCodeRepository: IVerificationCodeRepository,
    private val refreshTokenRepository: IRefreshTokenRepository,
    private val merchantApplicationRepository: IMerchantApplicationRepository
) : IAuthService {

    override fun registerUser(registrationRequest: RegistrationRequest): RegistrationResponse {
        if (registrationRequest.email.isNullOrEmpty() && registrationRequest.phone.isNullOrEmpty()) {
            throw IllegalArgumentException("邮箱或手机号至少需要一个")
        }

        // 验证提供的邮箱或手机号格式
        if (!registrationRequest.email.isNullOrEmpty() && !registrationRequest.email.isEmail()) {
            throw IllegalArgumentException("无效的邮箱格式")
        }
        if (!registrationRequest.phone.isNullOrEmpty() && !registrationRequest.phone.isPhoneNumber()) {
            throw IllegalArgumentException("无效的手机号格式")
        }

        // 检查重复的邮箱或手机号
        if (userRepository.checkDuplicateEmailOrPhone(registrationRequest.email, registrationRequest.phone)) {
            throw IllegalArgumentException("邮箱或手机号已被使用")
        }

        // 根据提供的信息，判断是通过哪种方式注册的，并据此设置验证状态
        val isEmailVerified = !registrationRequest.email.isNullOrEmpty()
        val isPhoneVerified = !registrationRequest.phone.isNullOrEmpty()

        // 密码加密处理
        val passwordHash = hashPassword(registrationRequest.password)

        // 创建用户对象，未提供的字段可以设置为null或默认值
        val newUser = User(
            userId = UUID.randomUUID().toString(), // 随机生成或其他方式
            username = "User${System.currentTimeMillis()}", // 或让用户稍后设置
            email = registrationRequest.email,
            phone = registrationRequest.phone,
            passwordHash = passwordHash,
            avatarUrl = null,
            bio = null,
            registerDate = LocalDateTime.now(),
            isEmailVerified = isEmailVerified,
            isPhoneVerified = isPhoneVerified,
            role = registrationRequest.role // 设置角色
        )

        userRepository.createUser(newUser)

        // 生成JWT令牌
        val token = tokenService.generateToken(newUser)
        val refreshToken = refreshTokenRepository.generateRefreshToken(newUser)

        return RegistrationResponse(success = true, message = "注册成功", userId = newUser.userId, token = token, refreshToken = refreshToken)
    }

    override fun sendVerificationCode(identifier: String, transData: SendCodeRequest): SendCodeResponse {
        val type = when(transData.operation) {
            "phone_register" -> VerificationType.PHONE_REGISTER
            "phone_login" -> VerificationType.PHONE_LOGIN
            "reset_password" -> VerificationType.RESET_PASSWORD
            "email_register" -> VerificationType.EMAIL_REGISTER
            "email_login" -> VerificationType.EMAIL_LOGIN
            "bind_email" -> VerificationType.BIND_EMAIL
            "bind_phone" -> VerificationType.BIND_PHONE
            else -> throw IllegalArgumentException("无效的操作类型")
        }

        // 根据identifier的类型决定调用短信服务还是邮件服务
        return if (identifier.isPhoneNumber()) {
            // 调用短信服务发送验证码
            val smsService = AliyunSmsService()
            smsService.generateAndSendAndSaveVerification(identifier, type) // 假设sendSMS返回SendCodeResponse对象
        } else if (identifier.isEmail()) {
            // 调用邮件服务发送验证码
            val emailService = AliyunEmailService()
            emailService.sendEmailViaAliyun(identifier, type) // 假设sendEmailViaAliyun返回SendCodeResponse对象
        } else {
            // 如果既不是电话号码也不是电子邮件地址，抛出异常或返回错误响应
            SendCodeResponse(
                success = false,
                message = "无效的邮箱地址或手机号码"
            )
        }
    }

    override fun loginWithPassword(identifier: String, password: String): LoginResponse {
        val user = userRepository.findUserByEmailOrPhone(identifier) ?: return LoginResponse(
            success = false,
            token = null,
            refreshToken = null,
            errorMessage = "用户名或密码错误"
        )

        if (!verifyPassword(password, user.passwordHash)) {
            return LoginResponse(
                success = false,
                token = null,
                refreshToken = null,
                errorMessage = "用户名或密码错误"
            )
        }

        val accessToken = tokenService.generateToken(user)
        val refreshToken = refreshTokenRepository.generateRefreshToken(user)
        return LoginResponse(
            success = true,
            token = accessToken,
            refreshToken = refreshToken,
            errorMessage = null
        )
    }

    override fun loginWithVerificationCode(identifier: String, code: String): LoginResponse {
        // 查找用户
        val user = userRepository.findUserByEmailOrPhone(identifier)
            ?: return LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "用户不存在")

        // 验证验证码并标记为已使用
        val isValidCode = verificationCodeRepository.verifyAndMarkCodeAsUsed(identifier, code)
        if (!isValidCode) {
            return LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "验证码无效或已过期")
        }

        // 生成 JWT 令牌
        val accessToken = tokenService.generateToken(user)
        val refreshToken = refreshTokenRepository.generateRefreshToken(user)

        // 删除过期验证码
        verificationCodeRepository.deleteExpiredVerificationCodes()

        return LoginResponse(success = true, token = accessToken, refreshToken = refreshToken, errorMessage = null)
    }


    override fun refreshToken(refreshToken: String): LoginResponse {
        val userId = refreshTokenRepository.getUserIdFromToken(refreshToken)
            ?: return LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "无效的刷新令牌")

        if (!refreshTokenRepository.validateRefreshToken(refreshToken)) {
            return LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "刷新令牌已过期")
        }

        val user = userRepository.findUserById(userId)
            ?: return LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "用户不存在")

        val newAccessToken = tokenService.generateToken(user)
        val newRefreshToken = refreshTokenRepository.generateRefreshToken(user)

        // 可以选择撤销旧的刷新令牌
        refreshTokenRepository.deleteRefreshToken(refreshToken)

        return LoginResponse(success = true, token = newAccessToken, refreshToken = newRefreshToken, errorMessage = null)
    }


    override fun verifyCode(identifier: String, code: String): Boolean {
        // 使用IVerificationCodeRepository的方法来验证验证码
        return verificationCodeRepository.verifyCode(identifier, code)
    }

    override fun resetPassword(identifier: String, newPassword: String): Boolean {
        val newPasswordHash = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray())

        val user = userRepository.findUserByEmailOrPhone(identifier)
            ?: return false // 未找到用户，返回失败

        val result = userRepository.resetUserPassword(user.userId, newPasswordHash)
        return result
    }

    override fun becomeSeller(userId: String): Boolean {
        return userRepository.updateUserRole(userId, Role.MERCHANT)
    }

    override fun logout(userId: String): Boolean {
        return try {
            // 删除用户的所有刷新令牌
            val tokensDeleted = refreshTokenRepository.deleteRefreshTokensByUserId(userId)

            // 如果你有其他需要在登出时清理的数据或状态，可以在这里进行
            // 例如，清理缓存的用户信息，或记录登出时间等。

            tokensDeleted
        } catch (e: Exception) {
            println("登出操作失败: ${e.localizedMessage}")
            false
        }
    }


    override fun changeAvatar(userId: String, avatarUrl: String): Boolean {
        return userRepository.updateUserAvatar(userId, avatarUrl)
    }

    override fun bindPhone(userId: String, newPhone: String, oldPhoneVerificationCode: String?): Boolean {
        val user = userRepository.findUserById(userId)
            ?: throw IllegalArgumentException("用户不存在")

        // 检查新手机号是否已被使用
        if (userRepository.checkDuplicateEmailOrPhone(null, newPhone)) {
            throw IllegalArgumentException("该手机号已被使用")
        }

        // 验证旧手机号的验证码（如果有旧手机号）
        user.phone?.let { oldPhone ->
            if (oldPhoneVerificationCode == null || !verificationCodeRepository.verifyCode(oldPhone, oldPhoneVerificationCode)) {
                throw IllegalArgumentException("旧手机号验证码错误或已过期")
            }
        }

        // 更新手机号
        return userRepository.updateUserPhone(userId, newPhone)
    }

    override fun bindEmail(userId: String, newEmail: String, oldEmailVerificationCode: String?): Boolean {
        val user = userRepository.findUserById(userId)
            ?: throw IllegalArgumentException("用户不存在")

        // 检查新邮箱是否已被使用
        if (userRepository.checkDuplicateEmailOrPhone(newEmail, null)) {
            throw IllegalArgumentException("该邮箱已被使用")
        }

        // 验证旧邮箱的验证码（如果有旧邮箱）
        user.email?.let { oldEmail ->
            if (oldEmailVerificationCode == null || !verificationCodeRepository.verifyCode(oldEmail, oldEmailVerificationCode)) {
                throw IllegalArgumentException("旧邮箱验证码无效")
            }
        }

        // 更新邮箱
        return userRepository.updateUserEmail(userId, newEmail)
    }

    override fun addAccount(request: AddAccountRequest, currentUserRole: Role): Boolean {
        // 仅允许超级管理员添加用户
        if (currentUserRole != Role.SUPER_ADMIN) {
            throw IllegalArgumentException("权限不足，无法添加用户")
        }

        // 检查邮箱或手机号是否已被使用
        if (userRepository.checkDuplicateEmailOrPhone(request.email, request.phone)) {
            throw IllegalArgumentException("邮箱或手机号已被使用")
        }

        val newUser = User(
            userId = generateUUID(),
            username = request.username,
            email = request.email,
            phone = request.phone,
            passwordHash = hashPassword(request.password),
            avatarUrl = null,
            bio = null,
            registerDate = LocalDateTime.now(),
            isEmailVerified = false,
            isPhoneVerified = false,
            role = request.role
        )

        return userRepository.createUserAndReturnUser(newUser)
    }

    override fun applyForMerchant(userId: String, application: MerchantApplication): Boolean {
        // 检查用户是否已经提交过申请
        val existingApplication = merchantApplicationRepository.findApplicationByUserId(userId)
        if (existingApplication != null) {
            throw IllegalArgumentException("您已经提交过商家申请，正在审核中")
        }

        // 保存商家申请信息
        val applicationWithId = application.copy(applicationId = UUID.randomUUID().toString())
        return merchantApplicationRepository.saveApplication(applicationWithId)
    }

    override fun approveMerchantApplication(userId: String): Boolean {
        // 查找商家申请
        val application = merchantApplicationRepository.findApplicationByUserId(userId)
            ?: throw IllegalArgumentException("未找到商家申请")

        // 更新申请状态为通过
        val updated = merchantApplicationRepository.updateApplicationStatus(userId, ApplicationStatus.APPROVED)

        // 如果申请通过，更新用户角色为商家
        if (updated) {
            return userRepository.updateUserRole(userId, Role.MERCHANT)
        }
        return false
    }

    override fun updateUserInfo(userId: String, updateRequest: UserInfoUpdateRequest): Boolean {
        // 获取当前用户信息
        val user = userRepository.findUserById(userId)
            ?: throw IllegalArgumentException("用户不存在")

        // 检查新邮箱或手机号是否已被其他用户使用
        if (updateRequest.email != null && userRepository.checkDuplicateEmailOrPhone(updateRequest.email, null)) {
            throw IllegalArgumentException("邮箱已被其他用户使用")
        }

        if (updateRequest.phone != null && userRepository.checkDuplicateEmailOrPhone(null, updateRequest.phone)) {
            throw IllegalArgumentException("手机号已被其他用户使用")
        }

        // 更新用户信息
        val updatedUser = user.copy(
            username = updateRequest.username ?: user.username,
            email = updateRequest.email ?: user.email,
            phone = updateRequest.phone ?: user.phone,
            avatarUrl = updateRequest.avatarUrl ?: user.avatarUrl,
            bio = updateRequest.bio ?: user.bio
        )

        return userRepository.updateUser(updatedUser)
    }

    private fun String.isPhoneNumber(): Boolean {
        val regex = Regex("""^\d{11}$""")
        return regex.matches(this)
    }

    private fun String.isEmail(): Boolean {
        val regex = Regex("""^[^@]+@[^@]+\.[^@]+$""")
        return regex.matches(this)
    }

    private fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }

    private fun verifyPassword(password: String, hashedPassword: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword)
        return result.verified
    }
}