package com.example.services.thirdPartyProviderServices

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

// 创建支付宝支付订单并返回支付链接
class AlipayService(
    private val client: HttpClient,
    private val apiKey: String,
    private val appId: String,
    private val appPrivateKey: String,
    private val alipayPublicKey: String
) {
    suspend fun createPayment(paymentData: Map<String, Any>): HttpResponse {
        return client.post("https://openapi.alipay.com/gateway.do") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                append("AppId", appId)
                append("AppPrivateKey", appPrivateKey)
                append("AlipayPublicKey", alipayPublicKey)
            }
            contentType(ContentType.Application.Json)
            setBody(paymentData)
        }
    }

    suspend fun handleCallback(callbackData: Map<String, String>): String {
        // 根据具体需求处理支付宝回调数据
        return "Success"
    }
}
