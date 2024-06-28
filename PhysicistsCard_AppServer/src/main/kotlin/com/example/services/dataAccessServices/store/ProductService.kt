package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.Product
import com.example.repositories.store.ProductRepository

class ProductService(private val productRepository: ProductRepository) : IProductService {

    override fun addProduct(product: Product): Product {
        return productRepository.add(product)
    }

    override fun getProductById(productId: String): Product? {
        return productRepository.findById(productId)
    }

    override fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    override fun updateProduct(product: Product): Product {
        return productRepository.update(product)
    }

    override fun deleteProduct(productId: String): Boolean {
        return productRepository.delete(productId)
    }

    override fun getProductsByCategory(categoryId: String): List<Product> {
        TODO("Not yet implemented")
    }

    override fun searchProducts(keywords: String): List<Product> {
        TODO("Not yet implemented")
    }
}
