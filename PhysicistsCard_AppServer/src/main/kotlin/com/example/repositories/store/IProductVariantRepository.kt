package com.example.repositories.store

import com.example.models.transmissionModels.store.ProductVariant
import com.example.repositories.Repository

interface IProductVariantRepository : Repository<ProductVariant,String> {
    fun findByProductId(productId: String): List<ProductVariant>
    // 根据基础商品ID查找其所有变体，用于展示商品的不同版本或配置。
}