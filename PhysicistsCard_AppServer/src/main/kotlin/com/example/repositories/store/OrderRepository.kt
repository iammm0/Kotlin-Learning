package com.example.repositories.store

import com.example.models.databaseTableModels.store.order.*
import com.example.models.transmissionModels.store.Order
import com.example.models.transmissionModels.store.OrderHistory
import com.example.models.transmissionModels.store.OrderItem
import com.example.models.transmissionModels.store.OrderStatus
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class OrderRepository : IOrderRepository{

    private val paymentInfoRepository = PaymentInfoRepository()
    private val shippingInfoRepository = ShippingInfoRepository()

    override fun findByCustomerId(customerId: Int): List<Order?> = transaction {
        Orders.selectAll().where { Orders.customerId eq customerId }
            .map { row -> row.toOrder() }
    }

    override fun findOrderItems(orderId: Int): List<OrderItem> = transaction {
        OrderItems.selectAll().where { OrderItems.orderId eq orderId }
            .map { row -> row.toOrderItem() }
    }

    override fun updateOrderStatus(orderId: Int, status: OrderStatus): Order? = transaction {
        Orders.update({ Orders.id eq orderId }) {
            it[Orders.status] = status
            it[updatedAt] = LocalDateTime.now()
        }
        Orders.selectAll().where { Orders.id eq orderId }.singleOrNull()?.toOrder()
    }

    override fun addOrderHistory(orderId: Int, history: OrderHistory): OrderHistory = transaction {
        val newHistoryId = OrderHistories.insert {
            it[OrderHistories.orderId] = orderId
            it[status] = history.status
            it[changedAt] = history.changedAt
            it[changeReason] = history.changeReason
        } get OrderHistories.historyId

        OrderHistories.selectAll().where { OrderHistories.historyId eq newHistoryId }.single().toOrderHistory()
    }

    private fun findOrderHistoriesByOrderId(orderId: Int): List<OrderHistory> = transaction {
        OrderHistories.selectAll().where { OrderHistories.orderId eq orderId }
            .map { it.toOrderHistory() }
    }

    private fun ResultRow.toOrder(): Order? {
        val orderId = this[Orders.id].value  // 获取订单ID
        val orderItems = findOrderItems(orderId)  // 获取订单项
        val paymentInfo = paymentInfoRepository.findByOrderId(orderId)  // 获取支付信息
        val shippingInfo = shippingInfoRepository.findByOrderId(orderId)  // 获取配送信息
        val orderHistory = findOrderHistoriesByOrderId(orderId)  // 获取订单历史记录

        return this[Orders.updatedAt]?.let {
            Order(
                orderId = orderId,
                customerId = this[Orders.customerId],
                items = orderItems,
                status = this[Orders.status],
                totalAmount = this[Orders.totalAmount],
                createdAt = this[Orders.createdAt],
                updatedAt = it,
                paymentInfo = paymentInfo,
                shippingInfo = shippingInfo,
                orderHistory = orderHistory
            )
        }
    }



    private fun ResultRow.toOrderItem(): OrderItem {
        return OrderItem(
            itemId = this[OrderItems.itemId],
            orderId = this[OrderItems.orderId],
            productId = this[OrderItems.productId],
            productName = this[OrderItems.productName],
            productImage = this[OrderItems.productImage],
            quantity = this[OrderItems.quantity],
            pricePerItem = this[OrderItems.pricePerItem],
            productSpec = this[OrderItems.productSpec]
        )
    }

    private fun ResultRow.toOrderHistory(): OrderHistory {
        return OrderHistory(
            historyId = this[OrderHistories.historyId],
            orderId = this[OrderHistories.orderId],
            status = this[OrderHistories.status],
            changeReason = this[OrderHistories.changeReason],
            changedAt = this[OrderHistories.changedAt]
        )
    }

    override fun add(item: Order): Order = transaction {
        val orderId = Orders.insertAndGetId {
            it[customerId] = item.customerId
            it[status] = item.status
            it[totalAmount] = item.totalAmount
            it[createdAt] = LocalDateTime.now()  // Assuming the current time is the creation time
            it[updatedAt] = LocalDateTime.now()  // Assuming immediate update after creation
        }.value
        item.copy(orderId = orderId)
    }

    override fun findById(id: Int): Order? = transaction {
        Orders.selectAll().where { Orders.id eq id }  // 使用 eq 比较操作符正确地筛选 id
            .singleOrNull()
            ?.toOrder()  // 确保 toOrder 是正确定义的转换函数
    }

    override fun findAll(): List<Order?> = transaction {
        Orders.selectAll()
            .map { it.toOrder() }
    }

    override fun update(item: Order): Order = transaction {
        Orders.update({ Orders.id eq item.orderId }) {  // 假设 orderId 是一个可以转换为 Int 的字符串
            it[customerId] = item.customerId
            it[status] = item.status
            it[totalAmount] = item.totalAmount
            it[updatedAt] = LocalDateTime.now()
        }
        findById(item.orderId) ?: throw IllegalStateException("Order with ID ${item.orderId} not found")
    }

    override fun delete(id: Int): Boolean = transaction {
        Orders.deleteWhere { Orders.id eq id } > 0  // 删除操作，并检查是否确实删除了至少一行
    }

}
