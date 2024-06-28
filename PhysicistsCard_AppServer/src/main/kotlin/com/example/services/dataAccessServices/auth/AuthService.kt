package com.example.services.dataAccessServices.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.models.databaseTableModels.auth.user.User
import com.example.models.transmissionModels.auth.VerificationType
import com.example.models.transmissionModels.auth.requests.RegistrationRequest
import com.example.models.transmissionModels.auth.requests.SendCodeRequest
import com.example.models.transmissionModels.auth.responses.RegistrationResponse
import com.example.models.transmissionModels.auth.responses.SendCodeResponse
import com.example.repositories.auth.UserRepository
import com.example.repositories.auth.VerificationCodeRepository
import com.example.services.thirdPartyProviderServices.AliyunEmailService
import com.example.services.thirdPartyProviderServices.AliyunSmsService
import com.example.utils.hashPassword
import java.time.LocalDateTime
import java.util.*

class AuthService : IAuthService {
    private val userRepository = UserRepository()
    private val verificationCodeRepository = VerificationCodeRepository()

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
            isPhoneVerified = isPhoneVerified
        )

        userRepository.createUser(newUser)

        return RegistrationResponse(success = true,"注册成功", newUser.userId)
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

    override fun loginWithPassword(identifier: String, password: String): Boolean {
        // 使用userRepository查询用户信息
        val user = userRepository.findUserByEmailOrPhone(identifier)
            ?: return false // 用户不存在

        // 验证密码（假设使用bcrypt进行哈希）
        val isPasswordValid = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash).verified
        return isPasswordValid
    }

    override fun loginWithVerificationCode(identifier: String, code: String): Boolean {
        // 根据identifier找到对应的用户
        val user = userRepository.findUserByEmailOrPhone(identifier)
            ?: return false // 用户不存在

        // 确定用于验证的标识符是邮箱还是电话号码
        val verifiedIdentifier = user.email ?: user.phone ?: return false

        // 检查是否有有效的、未过期的验证码
        val isValidCode = verificationCodeRepository.verifyCode(verifiedIdentifier, code)
        if (!isValidCode) {
            return false // 验证码无效或已过期
        }

        // 可选：登录成功后，清除已使用的验证码
        verificationCodeRepository.deleteExpiredVerificationCodes()

        return true
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

    private fun String.isPhoneNumber(): Boolean {
        val regex = Regex("""^\d{11}$""")
        return regex.matches(this)
    }

    private fun String.isEmail(): Boolean {
        val regex = Regex("""^[^@]+@[^@]+\.[^@]+$""")
        return regex.matches(this)
    }
}