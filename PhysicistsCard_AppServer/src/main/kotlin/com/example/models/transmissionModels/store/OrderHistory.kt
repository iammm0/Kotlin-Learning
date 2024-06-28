package com.example.models.transmissionModels.store

import com.example.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class OrderHistory(
    val historyId: String,
    val orderId: Int,
    val status: OrderStatus,
    val changeReason: String?, // 状态变更原因
    @Serializable(with = LocalDateTimeSerializer::class)
    val changedAt: LocalDateTime
)