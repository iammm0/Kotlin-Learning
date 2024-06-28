package com.example.models.databaseTableModels.store.product

import org.jetbrains.exposed.sql.Table

// 对于description和tags字段，您可以使用额外的表来存储这些信息
object ProductDescriptions : Table("ProductDescriptions") {
    val productId = reference("productId", Products)
    val language = varchar("language", 10)
    val text = text("text")

    override val primaryKey = PrimaryKey(productId, language, name = "PK_ProductDescriptions")
}