package com.example.models.transmissionModels.store.payment

import com.example.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class PaymentHistory(
    val historyId: String,
    val paymentId: String,
    val status: PaymentStatus,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateDate: LocalDateTime,
    val details: String? // 如支付失败的原因等
)