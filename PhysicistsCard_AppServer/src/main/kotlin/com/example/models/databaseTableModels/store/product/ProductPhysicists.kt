package com.example.models.databaseTableModels.store.product

import com.example.models.transmissionModels.store.Physicist
import org.jetbrains.exposed.sql.Table

object ProductPhysicists : Table("ProductPhysicists") {
    val productId = reference("productId", Products)
    val physicist = enumerationByName("physicist", 50, Physicist::class)
    override val primaryKey = PrimaryKey(productId, physicist, name = "PK_ProductPhysicists")
}