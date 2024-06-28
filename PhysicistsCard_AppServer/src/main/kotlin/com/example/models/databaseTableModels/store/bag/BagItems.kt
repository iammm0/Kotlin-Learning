package com.example.models.databaseTableModels.store.bag

import org.jetbrains.exposed.sql.Table

object BagItems : Table("BagItems") {
    val itemId = integer("itemId").autoIncrement()
    val bagId = varchar("bagId", 50).references(StoreBags.bagId)
    val productId = varchar("productId", 50)
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(itemId, name = "PK_BagItems_itemId")
}