package com.example.physicistscard.transmissionModels.store.shipping

import kotlinx.datetime.LocalDateTime

data class ShippingUpdate(
    val updateId: String,
    val shippingId: String,
    val status: ShippingStatus,
    val updateDate: LocalDateTime,
    val location: String?, // 当前位置
    val details: String? // 更新详情，如“已到达分拨中心”
)