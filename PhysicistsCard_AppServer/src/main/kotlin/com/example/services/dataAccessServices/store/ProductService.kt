package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.product.Product
import com.example.repositories.store.IProductRepository

class ProductService(private val productRepository: IProductRepository) : IProductService {

    override fun addProduct(product: Product): Product {
        return productRepository.add(product)
    }

    override fun getProductById(productId: String): Product? {
        return productRepository.findById(productId)
    }

    override fun getAllProducts(): List<Product> {
        return productRepository.findAll().filterNotNull()
    }

    override fun updateProduct(product: Product): Product {
        return productRepository.update(product)
    }

    override fun deleteProduct(productId: String): Boolean {
        return productRepository.delete(productId)
    }

    override fun getProductsByCategory(categoryId: String): List<Product> {
        // 这里需要categoryRepository的实现来获取产品类别信息
        return emptyList()
    }

    override fun searchProducts(keywords: String): List<Product> {
        // 这里实现搜索逻辑，可能需要全文检索库如ElasticSearch
        return emptyList()
    }
}
