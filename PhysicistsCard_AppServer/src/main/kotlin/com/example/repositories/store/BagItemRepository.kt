package com.example.repositories.store

import com.example.models.transmissionModels.store.BagItem
import com.example.models.databaseTableModels.store.bag.BagItems
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class BagItemRepository : IBagItemRepository {
    override fun findByCartId(cartId: String): List<BagItem> = transaction {
        BagItems.selectAll().where { BagItems.bagId eq cartId }
            .map { row -> row.toBagItem() }
    }

    private fun ResultRow.toBagItem(): BagItem {
        return BagItem(
            productId = this[BagItems.productId],
            quantity = this[BagItems.quantity]
        )
    }
}