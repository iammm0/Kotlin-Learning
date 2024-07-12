package com.example.physicistscard.transmissionModels.store.payment

import kotlinx.datetime.LocalDateTime

// 支付信息
data class PaymentInfo(
    val paymentId: String,
    val orderId: String,
    val paymentMethod: PaymentMethod, // 如 WECHAT_PAY, ALIPAY, UNION_PAY
    val amountPaid: Double,
    val paymentStatus: PaymentStatus, // 新增：支付状态，如 PENDING, COMPLETED, FAILED
    val paymentDate: LocalDateTime,
    val transactionId: String? // 新增：支付平台的交易ID
)