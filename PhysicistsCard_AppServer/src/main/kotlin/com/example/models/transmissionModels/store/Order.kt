package com.example.models.transmissionModels.store

import com.example.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

// Order模型应包含OrderItem列表，OrderItem与CartItem相似，也应包含商品的详细信息
@Serializable
data class Order(
    val orderId: Int,
    val customerId: Int, // 考虑直接关联用户信息
    val items: List<OrderItem>, // 订单项
    val status: OrderStatus, // 订单状态
    val totalAmount: Double, // 订单总金额
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime, // 创建时间
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime, // 更新时间
    val paymentInfo: PaymentInfo?, // 支付信息
    val shippingInfo: ShippingInfo?, // 配送信息
    val orderHistory: List<OrderHistory> // 订单状态历史
)











