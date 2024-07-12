package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.product.Product

interface IProductService {
    /**
     * 添加新产品
     *
     * @param product 产品对象
     * @return 新增的产品对象
     */
    fun addProduct(product: Product): Product

    /**
     * 通过产品ID获取产品
     *
     * @param productId 产品ID
     * @return 产品对象
     */
    fun getProductById(productId: String): Product?

    /**
     * 获取所有产品
     *
     * @return 产品列表
     */
    fun getAllProducts(): List<Product>

    /**
     * 更新产品
     *
     * @param product 产品对象
     * @return 更新后的产品对象
     */
    fun updateProduct(product: Product): Product

    /**
     * 删除产品
     *
     * @param productId 产品ID
     * @return 删除产品是否成功
     */
    fun deleteProduct(productId: String): Boolean

    /**
     * 通过分类获取产品
     *
     * @param categoryId 分类ID
     * @return 产品列表
     */
    fun getProductsByCategory(categoryId: String): List<Product>

    /**
     * 搜索产品
     *
     * @param keywords 搜索关键字
     * @return 产品列表
     */
    fun searchProducts(keywords: String): List<Product>
}
