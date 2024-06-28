package com.example.repositories.store

import com.example.models.transmissionModels.store.BagItem
import com.example.models.transmissionModels.store.StoreBag
import com.example.models.databaseTableModels.store.bag.StoreBags
import com.example.models.databaseTableModels.store.bag.BagItems
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class StoreBagRepository : IStoreBagRepository {

    override fun addItemToBag(cartId: String, item: BagItem): BagItem = transaction {
        BagItems.insert {
            it[bagId] = cartId
            it[productId] = item.productId
            it[quantity] = item.quantity
        }
        item
    }

    override fun removeItemFromBag(cartId: String, itemId: Int): Boolean = transaction {
        BagItems.deleteWhere { (BagItems.itemId eq itemId) and (bagId eq cartId) } > 0
    }

    override fun updateItemQuantity(cartId: String, itemId: Int, quantity: Int): BagItem? = transaction {
        BagItems.update({ (BagItems.itemId eq itemId) and (BagItems.bagId eq cartId) }) {
            it[BagItems.quantity] = quantity
        }
        BagItems.selectAll().where { (BagItems.itemId eq itemId) and (BagItems.bagId eq cartId) }
            .mapNotNull { it.toBagItem() }
            .singleOrNull()
    }

    override fun findItemsByCartId(cartId: String): List<BagItem> = transaction {
        BagItems.selectAll().where { BagItems.bagId eq cartId }
            .map { it.toBagItem() }
    }

    override fun clearBag(cartId: String): Boolean = transaction {
        BagItems.deleteWhere { bagId eq cartId } > 0
    }

    override fun add(item: StoreBag): StoreBag = transaction {
        StoreBags.insert {
            it[bagId] = item.bagId
            it[userId] = item.userId
        }
        item.items.forEach { addItemToBag(item.bagId, it) }
        item
    }

    override fun findById(id: String): StoreBag? = transaction {
        val items = findItemsByCartId(id)
        StoreBags.selectAll().where { StoreBags.bagId eq id }
            .map { StoreBag(it[StoreBags.bagId], it[StoreBags.userId], items) }
            .singleOrNull()
    }

    override fun findAll(): List<StoreBag?> = transaction {
        StoreBags.selectAll().map { storeBagRow ->
            val items = findItemsByCartId(storeBagRow[StoreBags.bagId])
            StoreBag(storeBagRow[StoreBags.bagId], storeBagRow[StoreBags.userId], items)
        }
    }

    override fun update(item: StoreBag): StoreBag = transaction {
        StoreBags.update({ StoreBags.bagId eq item.bagId }) {
            it[userId] = item.userId
        }
        clearBag(item.bagId)
        item.items.forEach { addItemToBag(item.bagId, it) }
        item
    }

    override fun delete(id: String): Boolean = transaction {
        StoreBags.deleteWhere { bagId eq id } > 0
    }

    private fun ResultRow.toBagItem() = BagItem(
        productId = this[BagItems.productId],
        quantity = this[BagItems.quantity]
    )
}
