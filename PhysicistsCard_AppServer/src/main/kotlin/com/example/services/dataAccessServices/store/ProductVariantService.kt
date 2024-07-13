package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.product.ProductVariant

class ProductVariantService : IProductVariantService {
    override fun findVariantsByProductId(productId: Int): List<ProductVariant> {
        TODO("Not yet implemented")
    }

    override fun addProductVariant(variant: ProductVariant): ProductVariant {
        TODO("Not yet implemented")
    }

    override fun updateProductVariant(variant: ProductVariant): ProductVariant {
        TODO("Not yet implemented")
    }

    override fun deleteProductVariant(variantId: String): Boolean {
        TODO("Not yet implemented")
    }
}