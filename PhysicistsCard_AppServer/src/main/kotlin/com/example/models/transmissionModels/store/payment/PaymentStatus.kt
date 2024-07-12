package com.example.models.transmissionModels.store.payment

import kotlinx.serialization.Serializable

@Serializable
enum class PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED
}