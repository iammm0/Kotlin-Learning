package com.example.physicistscard.transmissionModels.store.order

import kotlinx.datetime.LocalDateTime


data class OrderHistory(
    val historyId: String,
    val orderId: Int,
    val status: OrderStatus,
    val changeReason: String?, // 状态变更原因
    val changedAt: LocalDateTime
)