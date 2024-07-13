package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.shipping.ShippingInfo
import com.example.models.transmissionModels.store.shipping.ShippingStatus

class ShippingService : IShippingService {
    override fun addShippingInfo(orderId: String, shippingInfo: ShippingInfo): Boolean {
        TODO("Not yet implemented")
    }

    override fun getShippingInfo(orderId: String): ShippingInfo? {
        TODO("Not yet implemented")
    }

    override fun updateShippingStatus(orderId: String, status: ShippingStatus): Boolean {
        TODO("Not yet implemented")
    }
}