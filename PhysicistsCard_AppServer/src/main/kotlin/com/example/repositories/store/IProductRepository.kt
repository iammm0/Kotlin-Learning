package com.example.repositories.store

import com.example.models.transmissionModels.store.product.Era
import com.example.models.transmissionModels.store.product.PhysicsBranch
import com.example.models.transmissionModels.store.product.Product
import com.example.repositories.Repository

interface IProductRepository : Repository<Product, String> {
    /**
     * 根据商品内容时期筛选商品
     *
     * @param era 商品内容时期
     * @return 商品列表
     */
    fun findByEra(era: Era): List<Product>

    /**
     * 根据商品所属的物理学分支进行筛选
     *
     * @param branch 物理学分支
     * @return 商品列表
     */
    fun findByPhysicsBranch(branch: PhysicsBranch): List<Product>

    /**
     * 根据商品标签进行筛选
     *
     * @param tag 标签
     * @return 商品列表
     */
    fun findByTag(tag: String): List<Product>

    /**
     * 为指定商品添加标签
     *
     * @param productId 商品ID
     * @param tag 标签
     * @return 添加标签是否成功
     */
    fun addTag(productId: String, tag: String): Boolean

    /**
     * 移除指定商品的标签
     *
     * @param productId 商品ID
     * @param tag 标签
     * @return 移除标签是否成功
     */
    fun removeTag(productId: String, tag: String): Boolean
}
