package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.product.Category
import com.example.repositories.store.ICategoryRepository

class CategoryService(private val categoryRepository: ICategoryRepository) : ICategoryService {

    override fun addCategory(category: Category): Category {
        return categoryRepository.add(category)
    }

    override fun getAllCategories(): List<Category> {
        return categoryRepository.findAll().filterNotNull()
    }

    override fun getCategoryById(categoryId: String): Category? {
        return categoryRepository.findById(categoryId)
    }

    override fun updateCategory(category: Category): Category {
        return categoryRepository.update(category)
    }

    override fun deleteCategory(categoryId: String): Boolean {
        return categoryRepository.delete(categoryId)
    }
}
