package com.example.repositories.store

import com.example.models.transmissionModels.store.ShippingInfo
import com.example.models.transmissionModels.store.ShippingStatus
import com.example.repositories.Repository

interface IShippingInfoRepository : Repository<ShippingInfo,String> {
    fun findByOrderId(orderId: Int): ShippingInfo?
    // 根据订单ID查询配送信息，用于追踪订单的配送状态。

    fun updateShippingStatus(shippingId: String, status: ShippingStatus): ShippingInfo?
    // 更新配送状态，例如从“处理中”更新为“已发货”，用于配送状态管理。
}