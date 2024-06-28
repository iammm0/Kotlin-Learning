package com.example.models.databaseTableModels.store.product

import org.jetbrains.exposed.sql.Table

object ProductTags : Table("ProductTags") {
    val productId = reference("productId", Products).index()
    val tag = varchar("tag", 50)
    override val primaryKey = PrimaryKey(productId, tag, name = "PK_ProductTags")
}