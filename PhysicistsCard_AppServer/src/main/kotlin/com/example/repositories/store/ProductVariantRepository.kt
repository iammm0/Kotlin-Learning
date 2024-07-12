package com.example.repositories.store

import com.example.models.transmissionModels.store.product.ProductVariant
import com.example.models.databaseTableModels.store.product.ProductVariants
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ProductVariantRepository : IProductVariantRepository {

    override fun findByProductId(productId: String): List<ProductVariant> = transaction {
        ProductVariants.selectAll().where { ProductVariants.productId eq productId.toInt() }
            .map { it.toProductVariant() }
    }

    override fun add(item: ProductVariant): ProductVariant = transaction {
        val insertId = ProductVariants.insert {
            it[variantId] = item.variantId
            it[productId] = item.productId.toInt()
            it[name] = item.name
            it[stockQuantity] = item.stockQuantity
            it[additionalPrice] = item.additionalPrice
        } get ProductVariants.variantId

        findById(insertId)!!
    }

    override fun findById(id: String): ProductVariant? = transaction {
        ProductVariants.selectAll().where { ProductVariants.variantId eq id }
            .mapNotNull { it.toProductVariant() }
            .singleOrNull()
    }

    override fun findAll(): List<ProductVariant?> = transaction {
        ProductVariants.selectAll().map { it.toProductVariant() }
    }

    override fun update(item: ProductVariant): ProductVariant = transaction {
        ProductVariants.update({ ProductVariants.variantId eq item.variantId }) {
            it[name] = item.name
            it[stockQuantity] = item.stockQuantity
            it[additionalPrice] = item.additionalPrice
        }

        findById(item.variantId)!!
    }

    override fun delete(id: String): Boolean = transaction {
        ProductVariants.deleteWhere { variantId eq id } > 0
    }

    private fun ResultRow.toProductVariant() = ProductVariant(
        variantId = this[ProductVariants.variantId],
        productId = this[ProductVariants.productId].value.toString(),
        name = this[ProductVariants.name],
        stockQuantity = this[ProductVariants.stockQuantity],
        additionalPrice = this[ProductVariants.additionalPrice]
    )
}
