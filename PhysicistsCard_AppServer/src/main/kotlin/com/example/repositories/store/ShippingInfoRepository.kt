package com.example.repositories.store

import com.example.models.transmissionModels.store.shipping.ShippingInfo
import com.example.models.databaseTableModels.store.shipping.ShippingInfos
import com.example.models.databaseTableModels.store.shipping.ShippingInfos.shippingId
import com.example.models.transmissionModels.store.shipping.ShippingStatus
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ShippingInfoRepository : IShippingInfoRepository {

    override fun add(item: ShippingInfo): ShippingInfo = transaction {
        ShippingInfos.insert {
            it[shippingId] = item.shippingId
            it[orderId] = item.orderId.toInt()  // 假设orderId是整型
            it[carrier] = item.carrier
            it[trackingNumber] = item.trackingNumber
            it[shippingStatus] = item.shippingStatus
            it[shippedDate] = item.shippedDate
            it[estimatedDeliveryDate] = item.estimatedDeliveryDate
            it[actualDeliveryDate] = item.actualDeliveryDate
        }
        item  // 返回插入的对象
    }

    override fun findById(id: String): ShippingInfo? = transaction {
        ShippingInfos.selectAll().where { shippingId eq id }
            .mapNotNull { it.toShippingInfo() }
            .singleOrNull()  // 确保只有一个结果返回
    }

    override fun findByOrderId(orderId: Int): ShippingInfo? = transaction {
        ShippingInfos.selectAll().where { ShippingInfos.orderId eq orderId }
            .mapNotNull { it.toShippingInfo() }
            .singleOrNull()
    }

    override fun updateShippingStatus(shippingId: String, status: ShippingStatus): ShippingInfo? = transaction {
        ShippingInfos.update({ ShippingInfos.shippingId eq shippingId }) {
            it[shippingStatus] = status
        }
        findById(shippingId)  // 更新状态后返回更新的对象
    }

    override fun update(item: ShippingInfo): ShippingInfo = transaction {
        ShippingInfos.update({ shippingId eq item.shippingId }) {
            it[carrier] = item.carrier
            it[trackingNumber] = item.trackingNumber
            it[shippingStatus] = item.shippingStatus
            it[shippedDate] = item.shippedDate
            it[estimatedDeliveryDate] = item.estimatedDeliveryDate
            it[actualDeliveryDate] = item.actualDeliveryDate
        }
        findById(item.shippingId)!!  // 确认更新后返回
    }

    override fun findAll(): List<ShippingInfo?> = transaction {
        ShippingInfos.selectAll().map { it.toShippingInfo() }
    }

    override fun delete(id: String): Boolean = transaction {
        ShippingInfos.deleteWhere { shippingId eq id } > 0  // 删除指定ID的记录，并返回是否成功
    }

    private fun ResultRow.toShippingInfo() = ShippingInfo(
        shippingId = this[shippingId],
        orderId = this[ShippingInfos.orderId].toString(),  // 将Int转为String
        carrier = this[ShippingInfos.carrier],
        trackingNumber = this[ShippingInfos.trackingNumber],
        shippingStatus = this[ShippingInfos.shippingStatus],
        shippedDate = this[ShippingInfos.shippedDate],
        estimatedDeliveryDate = this[ShippingInfos.estimatedDeliveryDate],
        actualDeliveryDate = this[ShippingInfos.actualDeliveryDate]
    )
}
