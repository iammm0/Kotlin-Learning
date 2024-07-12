package com.example.physicistscard.transmissionModels.store.shipping

import com.example.physicistscard.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


// 物流信息
@Serializable
data class ShippingInfo(
    val shippingId: String,
    val orderId: String,
    val carrier: String, // 新增：承运人或物流公司
    val trackingNumber: String, // 物流单号
    val shippingStatus: ShippingStatus, // 新增：配送状态
    @Serializable(with = LocalDateTimeSerializer::class)
    val shippedDate: LocalDateTime?, // 发货时间
    @Serializable(with = LocalDateTimeSerializer::class)
    val estimatedDeliveryDate: LocalDateTime?, // 预计到达时间
    @Serializable(with = LocalDateTimeSerializer::class)
    val actualDeliveryDate: LocalDateTime? // 实际到达时间
)