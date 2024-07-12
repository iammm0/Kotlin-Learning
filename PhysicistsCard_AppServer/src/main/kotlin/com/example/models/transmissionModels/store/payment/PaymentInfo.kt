package com.example.models.transmissionModels.store.payment

import com.example.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

// 支付信息
@Serializable
data class PaymentInfo(
    val paymentId: String,
    val orderId: String,
    val paymentMethod: PaymentMethod, // 如 WECHAT_PAY, ALIPAY, UNION_PAY
    val amountPaid: Double,
    val paymentStatus: PaymentStatus, // 新增：支付状态，如 PENDING, COMPLETED, FAILED
    @Serializable(with = LocalDateTimeSerializer::class)
    val paymentDate: LocalDateTime,
    val transactionId: String? // 新增：支付平台的交易ID
)