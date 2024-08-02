package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.product.ProductVariant
import com.example.repositories.store.IProductVariantRepository

class ProductVariantService(private val productVariantRepository: IProductVariantRepository) : IProductVariantService {

    override fun findVariantsByProductId(productId: Int): List<ProductVariant> {
        return productVariantRepository.findByProductId(productId.toString())
    }

    override fun addProductVariant(variant: ProductVariant): ProductVariant {
        return productVariantRepository.add(variant)
    }

    override fun updateProductVariant(variant: ProductVariant): ProductVariant {
        return productVariantRepository.update(variant)
    }

    override fun deleteProductVariant(variantId: String): Boolean {
        return productVariantRepository.delete(variantId)
    }
}
