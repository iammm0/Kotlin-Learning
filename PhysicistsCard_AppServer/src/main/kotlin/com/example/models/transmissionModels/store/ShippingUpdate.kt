package com.example.models.transmissionModels.store

import com.example.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ShippingUpdate(
    val updateId: String,
    val shippingId: String,
    val status: ShippingStatus,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updateDate: LocalDateTime,
    val location: String?, // 当前位置
    val details: String? // 更新详情，如“已到达分拨中心”
)