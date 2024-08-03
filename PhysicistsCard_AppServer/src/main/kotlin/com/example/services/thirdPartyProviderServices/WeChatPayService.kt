package com.example.services.thirdPartyProviderServices

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

// 创建微信支付订单并返回支付信息
class WeChatPayService(
    private val client: HttpClient,
    private val apiKey: String,
    private val mchId: String,
    private val appId: String,
    private val appSecret: String
) {
    suspend fun createPayment(paymentData: Map<String, Any>): HttpResponse {
        return client.post("https://api.mch.weixin.qq.com/pay/unifiedorder") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                append("MchId", mchId)
                append("AppId", appId)
                append("AppSecret", appSecret)
            }
            contentType(ContentType.Application.Json)
            setBody(paymentData)
        }
    }

    suspend fun handleCallback(callbackData: Map<String, String>): String {
        // 根据具体需求处理微信支付回调数据
        return "Success"
    }
}
