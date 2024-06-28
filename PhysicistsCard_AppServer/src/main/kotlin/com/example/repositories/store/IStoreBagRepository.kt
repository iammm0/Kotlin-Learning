package com.example.repositories.store

import com.example.models.transmissionModels.store.BagItem
import com.example.models.transmissionModels.store.StoreBag
import com.example.repositories.Repository

interface IStoreBagRepository : Repository<StoreBag,String> {
    fun addItemToBag(cartId: String, item: BagItem): BagItem
    // 向指定购物车添加商品，如果商品已存在则更新数量。

    fun removeItemFromBag(cartId: String, itemId: Int): Boolean
    // 从购物车中移除指定商品。

    fun updateItemQuantity(cartId: String, itemId: Int, quantity: Int): BagItem?
    // 更新购物车中商品的数量。

    fun findItemsByCartId(cartId: String): List<BagItem>
    // 根据购物车ID查找其所有商品项。

    fun clearBag(cartId: String): Boolean
    // 清空指定购物车的所有商品项。
}