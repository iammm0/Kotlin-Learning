package com.example.physicistscard.transmissionModels.store.order


import com.example.physicistscard.transmissionModels.store.payment.PaymentInfo
import com.example.physicistscard.transmissionModels.store.shipping.ShippingInfo
import kotlinx.datetime.LocalDateTime


// Order模型应包含OrderItem列表，OrderItem与CartItem相似，也应包含商品的详细信息
data class Order(
    val orderId: Int,
    val customerId: Int, // 考虑直接关联用户信息
    val items: List<OrderItem>, // 订单项
    val status: OrderStatus, // 订单状态
    val totalAmount: Double, // 订单总金额
    val createdAt: LocalDateTime, // 创建时间
    val updatedAt: LocalDateTime, // 更新时间
    val paymentInfo: PaymentInfo?, // 支付信息
    val shippingInfo: ShippingInfo?, // 配送信息
    val orderHistory: List<OrderHistory> // 订单状态历史
)











