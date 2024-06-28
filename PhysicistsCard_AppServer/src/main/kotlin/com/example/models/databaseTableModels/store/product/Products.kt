package com.example.models.databaseTableModels.store.product

import com.example.models.transmissionModels.store.BundleType
import com.example.models.transmissionModels.store.Era
import com.example.models.transmissionModels.store.PhysicsBranch
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

// 使用IntIdTable自动生成整型ID作为主键
object Products : IntIdTable("Products") {
    val name = varchar("name", 255)
    val manufacturer = varchar("manufacturer", 255).nullable()
    val releaseDate = date("releaseDate").nullable()
    val price = double("price")
    val promotionalPrice = double("promotionalPrice").nullable()
    val isOnPromotion = bool("isOnPromotion")
    val stockQuantity = integer("stockQuantity")
    // imageUrls和tags需要额外的表来实现一对多关系
    val era = enumerationByName("era", 50, Era::class)
    // physicist需要额外的表来实现多对多关系
    val physicsBranch = enumerationByName("physicsBranch", 50, PhysicsBranch::class)
    val bundleType = enumerationByName("bundleType", 50, BundleType::class)
    val ratings = double("ratings").nullable()
    val reviewCount = integer("reviewCount").nullable()
}