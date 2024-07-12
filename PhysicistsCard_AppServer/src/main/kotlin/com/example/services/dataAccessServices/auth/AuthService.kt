package com.example.services.dataAccessServices.auth

import IAuthTokenRepository
import IUserRepository
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.models.transmissionModels.auth.requests.AddAccountRequest
import com.example.models.transmissionModels.auth.user.User
import com.example.models.transmissionModels.auth.verificationCodes.VerificationType
import com.example.models.transmissionModels.auth.requests.RegistrationRequest
import com.example.models.transmissionModels.auth.requests.SendCodeRequest
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
    private val tokenRepository: IAuthTokenRepository
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

        return RegistrationResponse(success = true, message = "注册成功", userId = newUser.userId, token = token)
    }

    override fun sendVerificationCode(identifier: String, transData: SendCodeRequest): SendCodeResponse {
        val type = when(transData.operation) {
            "phone_register" -> VerificationType.PHONE_REGISTER
            "phone_login" -> VerificationType.PHONE_LOGIN
            "reset_password" -> VerificationType.RESET_PASSWORD
            "email_register" -> VerificationType.EMAIL_REGISTER
            "email_login" -> VerificationType.EMAIL_LOGIN
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
                message = "Invalid identifier format."
            )
        }
    }

    override fun loginWithPassword(identifier: String, password: String): LoginResponse {
        val user = userRepository.findUserByEmailOrPhone(identifier) ?: return LoginResponse(
            success = false,
            token = null,
            errorMessage = "用户名或密码错误"
        )

        if (!verifyPassword(password, user.passwordHash)) {
            return LoginResponse(
                success = false,
                token = null,
                errorMessage = "用户名或密码错误"
            )
        }

        val token = tokenService.generateToken(user)
        return LoginResponse(success = true, token = token, errorMessage = null)
    }

    override fun loginWithVerificationCode(identifier: String, code: String): LoginResponse {
        val user = userRepository.findUserByEmailOrPhone(identifier)
            ?: return LoginResponse(success = false, token = null, errorMessage = "用户不存在")

        val verifiedIdentifier = user.email ?: user.phone ?: return LoginResponse(success = false, token = null, errorMessage = "无效的用户标识")

        val isValidCode = verificationCodeRepository.verifyCode(verifiedIdentifier, code)
        return if (isValidCode) {
            val token = tokenService.generateToken(user)
            verificationCodeRepository.deleteExpiredVerificationCodes()
            LoginResponse(success = true, token = token, errorMessage = null)
        } else {
            LoginResponse(success = false, token = null, errorMessage = "验证码无效或已过期")
        }
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
        return tokenRepository.deleteTokensByUserId(userId)
    }

    override fun changeAvatar(userId: String, avatarUrl: String): Boolean {
        return userRepository.updateUserAvatar(userId, avatarUrl)
    }

    override fun bindPhone(userId: String, phone: String): Boolean {
        return userRepository.updateUserPhone(userId, phone)
    }

    override fun bindEmail(userId: String, email: String): Boolean {
        return userRepository.updateUserEmail(userId, email)
    }

    override fun addAccount(request: AddAccountRequest): Boolean {
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