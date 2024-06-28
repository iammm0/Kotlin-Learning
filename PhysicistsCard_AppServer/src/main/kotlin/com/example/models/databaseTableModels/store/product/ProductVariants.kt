package com.example.models.databaseTableModels.store.product

import org.jetbrains.exposed.sql.Table

object ProductVariants : Table("ProductVariants") {
    val variantId = varchar("variantId", 50)
    val productId = reference("productId", Products)
    val name = varchar("name", 255)
    val stockQuantity = integer("stockQuantity")
    val additionalPrice = double("additionalPrice").nullable()

    override val primaryKey = PrimaryKey(variantId, name = "PK_ProductVariant_variantId")
}