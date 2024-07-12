package com.example.physicistscard.transmissionModels.store.payment

import kotlinx.datetime.LocalDateTime

data class PaymentHistory(
    val historyId: String,
    val paymentId: String,
    val status: PaymentStatus,
    val updateDate: LocalDateTime,
    val details: String? // 如支付失败的原因等
)