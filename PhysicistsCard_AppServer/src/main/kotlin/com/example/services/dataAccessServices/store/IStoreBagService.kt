package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.bag.BagItem

interface IStoreBagService {
    /**
     * 向购物车添加商品
     *
     * @param bagId 购物车ID
     * @param item 购物车商品项
     * @return 添加后的购物车商品项
     */
    fun addItemToBag(bagId: String, item: BagItem): BagItem

    /**
     * 从购物车中移除商品
     *
     * @param bagId 购物车ID
     * @param itemId 商品项ID
     * @return 移除是否成功
     */
    fun removeItemFromBag(bagId: String, itemId: Int): Boolean

    /**
     * 更新购物车中商品的数量
     *
     * @param bagId 购物车ID
     * @param itemId 商品项ID
     * @param quantity 新的商品数量
     * @return 更新后的购物车商品项
     */
    fun updateItemQuantity(bagId: String, itemId: Int, quantity: Int): BagItem?

    /**
     * 根据购物车ID查找商品项
     *
     * @param bagId 购物车ID
     * @return 商品项列表
     */
    fun findItemsByBagId(bagId: String): List<BagItem>

    /**
     * 清空购物车
     *
     * @param bagId 购物车ID
     * @return 清空是否成功
     */
    fun clearBag(bagId: String): Boolean
}

