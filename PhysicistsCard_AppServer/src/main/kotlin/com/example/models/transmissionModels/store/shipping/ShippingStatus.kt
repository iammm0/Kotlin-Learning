package com.example.models.transmissionModels.store.shipping

import kotlinx.serialization.Serializable

@Serializable
enum class ShippingStatus {
    PROCESSING, // 处理中
    IN_TRANSIT, // 运输中
    DELIVERED,  // 已送达
    RETURNED  // 已退回
}