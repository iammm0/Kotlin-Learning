package com.example.repositories.store

import com.example.models.transmissionModels.store.product.ProductVariant
import com.example.repositories.Repository

interface IProductVariantRepository : Repository<ProductVariant, String> {
    /**
     * 根据基础商品ID查找其所有变体
     *
     * @param productId 基础商品ID
     * @return 商品变体列表
     */
    fun findByProductId(productId: String): List<ProductVariant>
}
