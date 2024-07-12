package com.example.repositories.store

import com.example.models.databaseTableModels.store.product.*
import com.example.models.transmissionModels.store.product.Era
import com.example.models.transmissionModels.store.product.Physicist
import com.example.models.transmissionModels.store.product.PhysicsBranch
import com.example.models.transmissionModels.store.product.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


class ProductRepository : IProductRepository {
    override fun add(item: Product): Product = transaction {
        val productId = Products.insertAndGetId {
            it[name] = item.name
            it[manufacturer] = item.manufacturer
            it[releaseDate] = item.releaseDate
            it[price] = item.price
            it[promotionalPrice] = item.promotionalPrice
            it[isOnPromotion] = item.isOnPromotion
            it[stockQuantity] = item.stockQuantity
            it[era] = item.era
            it[physicsBranch] = item.physicsBranch
            it[bundleType] = item.bundleType
            it[ratings] = item.ratings
            it[reviewCount] = item.reviewCount
        }.value

        item.copy(productId = productId)
    }

    override fun findById(id: String): Product? = transaction {
        Products.selectAll().where { Products.id eq id.toInt() }
            .mapNotNull { it.toProduct() }
            .singleOrNull()
    }

    override fun findAll(): List<Product> = transaction {
        Products.selectAll().map { it.toProduct() }
    }

    override fun delete(id: String): Boolean = transaction {
        Products.deleteWhere { Products.id eq id.toInt() } > 0
    }

    override fun update(item: Product): Product = transaction {
        Products.update({ Products.id eq item.productId }) {
            it[name] = item.name
            it[manufacturer] = item.manufacturer
            it[releaseDate] = item.releaseDate
            it[price] = item.price
            it[promotionalPrice] = item.promotionalPrice
            it[isOnPromotion] = item.isOnPromotion
            it[stockQuantity] = item.stockQuantity
            it[era] = item.era
            it[physicsBranch] = item.physicsBranch
            it[bundleType] = item.bundleType
            it[ratings] = item.ratings
            it[reviewCount] = item.reviewCount
        }
        findById(item.productId.toString()) ?: throw IllegalStateException("Product with ID ${item.productId} not found")
    }

    override fun findByEra(era: Era): List<Product> = transaction {
        Products.selectAll().where { Products.era eq era }.map { it.toProduct() }
    }

    override fun findByPhysicsBranch(branch: PhysicsBranch): List<Product> = transaction {
        Products.selectAll().where { Products.physicsBranch eq branch }.map { it.toProduct() }
    }

    override fun findByTag(tag: String): List<Product> = transaction {
        (Products innerJoin ProductTags).selectAll().where { ProductTags.tag eq tag }.map { it.toProduct() }
    }

    override fun addTag(productId: String, tag: String): Boolean = transaction {
        val id = productId.toInt()  // Assuming productId is always convertible to Int
        val exists = ProductTags.selectAll().where { (ProductTags.productId eq id) and (ProductTags.tag eq tag) }.any()

        if (!exists) {
            ProductTags.insert {
                it[ProductTags.productId] = id
                it[ProductTags.tag] = tag
            }
            true
        } else false
    }

    override fun removeTag(productId: String, tag: String): Boolean = transaction {
        try {
            val id = productId.toInt()  // 尝试将 productId 转换为 Int
            ProductTags.deleteWhere {
                (ProductTags.productId eq id) and (ProductTags.tag eq tag)
            } > 0  // 判断是否成功删除了记录（删除行数 > 0）
        } catch (e: NumberFormatException) {
            false  // 如果 productId 不是一个有效的整数，返回 false
        }
    }


    private fun ResultRow.toProduct(): Product = Product(
        productId = this[Products.id].value,
        name = this[Products.name],
        description = getDescription(this[Products.id].value),  // 假设存在一个方法来获取描述
        manufacturer = this[Products.manufacturer],
        releaseDate = this[Products.releaseDate],
        price = this[Products.price],
        promotionalPrice = this[Products.promotionalPrice],
        isOnPromotion = this[Products.isOnPromotion],
        stockQuantity = this[Products.stockQuantity],
        imageUrls = getImageUrls(this[Products.id].value),  // 假设存在一个方法来获取图片 URLs
        tags = getTags(this[Products.id].value),  // 假设存在一个方法来获取标签
        era = this[Products.era],
        physicist = getPhysicists(this[Products.id].value),  // 假设存在一个方法来获取相关物理学家
        physicsBranch = this[Products.physicsBranch],
        bundleType = this[Products.bundleType],
        ratings = this[Products.ratings],
        reviewCount = this[Products.reviewCount]
    )

    private fun getDescription(productId: Int): Map<String, String> {
        return ProductDescriptions.selectAll().where { ProductDescriptions.productId eq productId }
            .associate { it[ProductDescriptions.language] to it[ProductDescriptions.text] }
    }

    private fun getImageUrls(productId: Int): List<String> {
        return emptyList()
    }

    private fun getTags(productId: Int): List<String> {
        return ProductTags.selectAll().where { ProductTags.productId eq productId }
            .map { it[ProductTags.tag] }
    }

    private fun getPhysicists(productId: Int): List<Physicist> {
        return ProductPhysicists.selectAll().where { ProductPhysicists.productId eq productId }
            .map { Physicist.valueOf(it[ProductPhysicists.physicist].name) }
    }

}
