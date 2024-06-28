package com.example.physicistscard.transmissionModels.store.shipping

import com.example.models.databaseTableModels.store.shipping.ShippingStatus
import kotlinx.datetime.LocalDateTime


// 物流信息
data class ShippingInfo(
    val shippingId: String,
    val orderId: String,
    val carrier: String, // 新增：承运人或物流公司
    val trackingNumber: String, // 物流单号
    val shippingStatus: ShippingStatus, // 新增：配送状态
    val shippedDate: LocalDateTime?, // 发货时间
    val estimatedDeliveryDate: LocalDateTime?, // 预计到达时间
    val actualDeliveryDate: LocalDateTime? // 实际到达时间
)