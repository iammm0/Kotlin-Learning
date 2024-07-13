package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.product.ProductVariant

interface IProductVariantService {
    /**
     * 根据基础商品ID查找其所有变体
     *
     * @param productId 基础商品ID
     * @return 商品变体列表
     */
    fun findVariantsByProductId(productId: Int): List<ProductVariant>

    /**
     * 添加商品变体
     *
     * @param variant 商品变体信息
     * @return 新增的商品变体对象
     */
    fun addProductVariant(variant: ProductVariant): ProductVariant

    /**
     * 更新商品变体信息
     *
     * @param variant 商品变体对象
     * @return 更新后的商品变体对象
     */
    fun updateProductVariant(variant: ProductVariant): ProductVariant

    /**
     * 删除商品变体
     *
     * @param variantId 商品变体ID
     * @return 删除是否成功
     */
    fun deleteProductVariant(variantId: String): Boolean
}
