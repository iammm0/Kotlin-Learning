package com.example.repositories.store

import com.example.models.transmissionModels.store.bag.BagItem
import com.example.models.transmissionModels.store.bag.StoreBag
import com.example.repositories.Repository

interface IStoreBagRepository : Repository<StoreBag, String> {
    /**
     * 向指定购物车添加商品
     *
     * @param cartId 购物车ID
     * @param item 商品项
     * @return 添加的商品项对象
     */
    fun addItemToBag(cartId: String, item: BagItem): BagItem

    /**
     * 从购物车中移除指定商品
     *
     * @param cartId 购物车ID
     * @param itemId 商品项ID
     * @return 移除是否成功
     */
    fun removeItemFromBag(cartId: String, itemId: Int): Boolean

    /**
     * 更新购物车中商品的数量
     *
     * @param cartId 购物车ID
     * @param itemId 商品项ID
     * @param quantity 更新后的数量
     * @return 更新后的商品项对象，或null表示更新失败
     */
    fun updateItemQuantity(cartId: String, itemId: Int, quantity: Int): BagItem?

    /**
     * 根据购物车ID查找其所有商品项
     *
     * @param cartId 购物车ID
     * @return 购物车内的商品项列表
     */
    fun findItemsByCartId(cartId: String): List<BagItem>

    /**
     * 清空指定购物车的所有商品项
     *
     * @param cartId 购物车ID
     * @return 清空是否成功
     */
    fun clearBag(cartId: String): Boolean
}
