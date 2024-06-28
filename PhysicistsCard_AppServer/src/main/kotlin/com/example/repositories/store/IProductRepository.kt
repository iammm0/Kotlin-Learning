package com.example.repositories.store

import com.example.models.transmissionModels.store.Era
import com.example.models.transmissionModels.store.PhysicsBranch
import com.example.models.transmissionModels.store.Product
import com.example.repositories.Repository

interface IProductRepository : Repository<Product,String> {
    fun findByEra(era: Era): List<Product>
    // 根据商品内容时期筛选商品，例如查找所有“现代”时期的商品。

    fun findByPhysicsBranch(branch: PhysicsBranch): List<Product>
    // 根据商品所属的物理学分支进行筛选。

    fun findByTag(tag: String): List<Product>
    // 根据商品标签进行筛选，例如查找所有标记为“限量”的商品。

    fun addTag(productId: String, tag: String): Boolean
    // 为指定商品添加标签，用于商品标签管理。

    fun removeTag(productId: String, tag: String): Boolean
    // 移除指定商品的标签。
}