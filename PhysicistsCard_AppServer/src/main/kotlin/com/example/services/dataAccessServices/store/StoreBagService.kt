package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.bag.BagItem
import com.example.repositories.store.IStoreBagRepository

class StoreBagService(private val storeBagRepository: IStoreBagRepository) : IStoreBagService {

    override fun addItemToBag(bagId: String, item: BagItem): BagItem {
        return storeBagRepository.addItemToBag(bagId, item)
    }

    override fun removeItemFromBag(bagId: String, itemId: Int): Boolean {
        return storeBagRepository.removeItemFromBag(bagId, itemId)
    }

    override fun updateItemQuantity(bagId: String, itemId: Int, quantity: Int): BagItem? {
        return storeBagRepository.updateItemQuantity(bagId, itemId, quantity)
    }

    override fun findItemsByBagId(bagId: String): List<BagItem> {
        return storeBagRepository.findItemsByCartId(bagId)
    }

    override fun clearBag(bagId: String): Boolean {
        return storeBagRepository.clearBag(bagId)
    }
}
