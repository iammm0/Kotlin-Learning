package com.example.services.thirdPartyProviderServices

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

// 创建顺丰订单并跟踪订单状态
class SFExpressService(private val client: HttpClient, private val apiKey: String, private val merchantId: String) {
    suspend fun createOrder(orderData: Map<String, Any>): HttpResponse {
        return client.post("https://api.sf-express.com/order/create") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $apiKey")
                append("MerchantId", merchantId)
            }
            contentType(ContentType.Application.Json)
            setBody(orderData)
        }
    }

    suspend fun trackOrder(orderId: String): HttpResponse {
        return client.get("https://api.sf-express.com/order/track") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $apiKey")
                append("MerchantId", merchantId)
            }
            parameter("orderId", orderId)
        }
    }
}
