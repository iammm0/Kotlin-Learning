package com.example.services.thirdPartyProviderServices

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.IAcsClient
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest
import com.aliyuncs.profile.DefaultProfile
import com.example.models.transmissionModels.auth.VerificationType
import com.example.models.transmissionModels.auth.responses.SendCodeResponse
import com.example.repositories.auth.VerificationCodeRepository
import java.util.concurrent.ThreadLocalRandom

class AliyunEmailService {
    private val regionId = "cn-hangzhou" // 根据实际情况填写
    private val accessKeyId = "LTAI5t67b92fs7a2azKG8KfL"
    private val secret = "5vOL1hXlOeEt2JDC07X3612M5rcGzg"
    private val fromAddress = "physicistscard@datianersi.cn"
    private val subject = "PHYSICISTS CARD 验证消息"
    private val verificationCode = VerificationCodeRepository()

    private fun generateRandomCode(): String {
        return (1..6).joinToString("") { ThreadLocalRandom.current().nextInt(0, 10).toString() }
    }

    fun sendEmailViaAliyun(emailAddress: String, type: VerificationType) : SendCodeResponse {
        val code = generateRandomCode()
        val content = """【双煎滑蛋科技文化创意有限公司】
        |您本次的 PhysCard 验证代码为$code，该验证码5分钟内有效。
        |祝您在 PHYSICISTS CARD 世界中体验愉快！""".trimMargin()

        val profile = DefaultProfile.getProfile(regionId, accessKeyId, secret)
        val client: IAcsClient = DefaultAcsClient(profile)

        val request = SingleSendMailRequest().apply {
            accountName = fromAddress
            toAddress = emailAddress
            subject = this@AliyunEmailService.subject
            htmlBody = content
            addressType = 1
            replyToAddress = true // 开启回信地址功能
            replyAddress = "physicisthacker@outlook.com"
        }

        verificationCode.saveVerificationCode(code, emailAddress, 5, type)

        return try {
            client.getAcsResponse(request)
            // 发送成功，构造并返回成功的响应
            SendCodeResponse(
                success = true,
                message = "邮件发送成功"
            )
        } catch (e: Exception) {
            // 发送失败，构造并返回失败的响应
            SendCodeResponse(
                success = false,
                message = "邮件发送失败，错误代码: ${e.message}"
            )
        }

    }
}