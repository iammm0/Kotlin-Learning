package com.example.repositories.store

import com.example.models.transmissionModels.store.shipping.ShippingInfo
import com.example.models.transmissionModels.store.shipping.ShippingStatus
import com.example.repositories.Repository

interface IShippingInfoRepository : Repository<ShippingInfo, String> {
    /**
     * 根据订单ID查询配送信息
     *
     * @param orderId 订单ID
     * @return 配送信息对象，或null表示未找到
     */
    fun findByOrderId(orderId: Int): ShippingInfo?

    /**
     * 更新配送状态
     *
     * @param shippingId 配送ID
     * @param status 新的配送状态
     * @return 更新后的配送信息对象，或null表示更新失败
     */
    fun updateShippingStatus(shippingId: String, status: ShippingStatus): ShippingInfo?
}
