package com.example.repositories.store

import com.example.models.transmissionModels.store.bag.BagItem

interface IBagItemRepository {
    /**
     * 根据购物车ID查询该购物车内的所有商品项
     *
     * @param cartId 购物车ID
     * @return 购物车内的商品项列表
     */
    fun findByCartId(cartId: String): List<BagItem>
}
