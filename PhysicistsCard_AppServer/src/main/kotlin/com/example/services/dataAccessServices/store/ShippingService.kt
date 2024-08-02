package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.shipping.ShippingInfo
import com.example.models.transmissionModels.store.shipping.ShippingStatus
import com.example.repositories.store.IShippingInfoRepository

class ShippingService(private val shippingInfoRepository: IShippingInfoRepository) : IShippingService {

    override fun addShippingInfo(orderId: String, shippingInfo: ShippingInfo): Boolean {
        shippingInfoRepository.add(shippingInfo)
        return true
    }

    override fun getShippingInfo(orderId: String): ShippingInfo? {
        return shippingInfoRepository.findByOrderId(orderId.toInt())
    }

    override fun updateShippingStatus(orderId: String, status: ShippingStatus): Boolean {
        val shippingInfo = shippingInfoRepository.findByOrderId(orderId.toInt())
        return shippingInfo?.let {
            shippingInfoRepository.updateShippingStatus(it.orderId, status)
            true
        } ?: false
    }
}
