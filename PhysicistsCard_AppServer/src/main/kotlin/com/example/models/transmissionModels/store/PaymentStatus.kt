package com.example.models.transmissionModels.store

import kotlinx.serialization.Serializable

@Serializable
enum class PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED
}