package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.Product

interface IProductService  {
    fun addProduct(product: Product): Product
    fun getProductById(productId: String): Product?
    fun getAllProducts(): List<Product>
    fun updateProduct(product: Product): Product
    fun deleteProduct(productId: String): Boolean
    fun getProductsByCategory(categoryId: String): List<Product>
    fun searchProducts(keywords: String): List<Product>
}
