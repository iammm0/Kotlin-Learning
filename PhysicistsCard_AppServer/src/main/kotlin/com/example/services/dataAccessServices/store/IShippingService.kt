package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.shipping.ShippingInfo
import com.example.models.transmissionModels.store.shipping.ShippingStatus

interface IShippingService {
    /**
     * 添加物流信息
     *
     * @param orderId 订单ID
     * @param shippingInfo 物流信息
     * @return 添加是否成功
     */
    fun addShippingInfo(orderId: String, shippingInfo: ShippingInfo): Boolean

    /**
     * 获取订单的物流信息
     *
     * @param orderId 订单ID
     * @return 物流信息
     */
    fun getShippingInfo(orderId: String): ShippingInfo?

    /**
     * 更新物流状态
     *
     * @param orderId 订单ID
     * @param status 新的物流状态
     * @return 更新是否成功
     */
    fun updateShippingStatus(orderId: String, status: ShippingStatus): Boolean
}
