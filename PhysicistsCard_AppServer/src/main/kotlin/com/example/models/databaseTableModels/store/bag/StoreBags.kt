package com.example.models.databaseTableModels.store.bag

import org.jetbrains.exposed.sql.Table

object StoreBags : Table("StoreBags") {
    val bagId = varchar("bagId", 50)
    val userId = varchar("userId", 50)

    override val primaryKey = PrimaryKey(StoreBags.bagId, name = "PK_StoreBags_cardId")
}