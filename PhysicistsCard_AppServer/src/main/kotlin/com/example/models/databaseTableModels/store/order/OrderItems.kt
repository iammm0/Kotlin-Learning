package com.example.models.databaseTableModels.store.order

import org.jetbrains.exposed.sql.Table

object OrderItems : Table("OrderItems") {
    val itemId = integer("itemId").autoIncrement()
    val orderId = integer("orderId").references(Orders.id)
    val productId = integer("productId")
    val productName = varchar("productName", 255)
    val productImage = varchar("productImage", 255)
    val quantity = integer("quantity")
    val pricePerItem = double("pricePerItem")
    val productSpec = varchar("productSpec", 255).nullable()

    override val primaryKey = PrimaryKey(itemId, name = "PK_OrderItems_itemId")
}