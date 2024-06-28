package com.example.repositories.store

import com.example.models.transmissionModels.store.BagItem

interface IBagItemRepository {
    fun findByCartId(cartId: String): List<BagItem>
    // 根据购物车ID查询该购物车内的所有商品项，显示购物车内容、计算购物车总价
}