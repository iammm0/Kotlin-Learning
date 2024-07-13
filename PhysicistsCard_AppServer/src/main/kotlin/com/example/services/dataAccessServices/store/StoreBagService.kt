package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.bag.BagItem

class StoreBagService : IStoreBagService {
    override fun addItemToBag(bagId: String, item: BagItem): BagItem {
        TODO("Not yet implemented")
    }

    override fun removeItemFromBag(bagId: String, itemId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateItemQuantity(bagId: String, itemId: Int, quantity: Int): BagItem? {
        TODO("Not yet implemented")
    }

    override fun findItemsByBagId(bagId: String): List<BagItem> {
        TODO("Not yet implemented")
    }

    override fun clearBag(bagId: String): Boolean {
        TODO("Not yet implemented")
    }
}