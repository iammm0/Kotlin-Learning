package com.example.services.thirdPartyProviderServices

import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.IAcsClient
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest
import com.aliyuncs.exceptions.ClientException
import com.aliyuncs.exceptions.ServerException
import com.aliyuncs.profile.DefaultProfile
import com.example.models.transmissionModels.auth.VerificationType
import com.example.models.transmissionModels.auth.responses.SendCodeResponse
import com.example.repositories.auth.VerificationCodeRepository
import java.util.concurrent.ThreadLocalRandom

class AliyunSmsService {
    private val regionId = "cn-hangzhou"
    private val accessKeyId = "LTAI5t67b92fs7a2azKG8KfL" //
    private val accessKeySecret = "5vOL1hXlOeEt2JDC07X3612M5rcGzg" //
    private val signName = "大天而思文化交流活动策划" //
    private val templateCode = "SMS_465409318" //
    private val verificationCode = VerificationCodeRepository()


    private fun generateRandomCode(): String {
        return (1..6).joinToString("") { ThreadLocalRandom.current().nextInt(0, 10).toString() }
    }

    fun generateAndSendAndSaveVerification(phoneNumber: String, type: VerificationType): SendCodeResponse {
        val code = generateRandomCode()
        val templateParam = "{\"code\":\"$code\"}" //
        val profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret)
        val client: IAcsClient = DefaultAcsClient(profile)

        val request = SendSmsRequest().apply {
            this.phoneNumbers = phoneNumber
            this.signName = this@AliyunSmsService.signName
            this.templateCode = this@AliyunSmsService.templateCode
            this.templateParam = templateParam
        }

        verificationCode.saveVerificationCode(code, phoneNumber, 5, type)


        return try {
            val response = client.getAcsResponse(request)
            if (response.code == "OK") {
                SendCodeResponse(success = true, message = "验证码发送成功")
            } else {
                SendCodeResponse(success = false, message = "短信发送失败: ${response.message}", errorCode = response.code)
            }
        } catch (e: ClientException) {
            e.printStackTrace()
            SendCodeResponse(success = false, message = "客户端错误", errorCode = e.errCode)
        } catch (e: ServerException) {
            e.printStackTrace()
            SendCodeResponse(success = false, message = "服务器错误", errorCode = e.errCode)
        } catch (e: Exception) {
            e.printStackTrace()
            SendCodeResponse(success = false, message = "未知错误", errorCode = "UnknownError")
        }
    }
}
