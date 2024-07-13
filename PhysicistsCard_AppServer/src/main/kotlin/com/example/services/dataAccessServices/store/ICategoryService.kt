package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.product.Category

interface ICategoryService {
    /**
     * 添加新类别
     *
     * @param category 类别信息
     * @return 新增的类别对象
     */
    fun addCategory(category: Category): Category

    /**
     * 获取所有类别
     *
     * @return 类别列表
     */
    fun getAllCategories(): List<Category>

    /**
     * 根据类别ID获取类别信息
     *
     * @param categoryId 类别ID
     * @return 类别对象
     */
    fun getCategoryById(categoryId: String): Category?

    /**
     * 更新类别信息
     *
     * @param category 类别对象
     * @return 更新后的类别对象
     */
    fun updateCategory(category: Category): Category

    /**
     * 删除类别
     *
     * @param categoryId 类别ID
     * @return 删除是否成功
     */
    fun deleteCategory(categoryId: String): Boolean
}
