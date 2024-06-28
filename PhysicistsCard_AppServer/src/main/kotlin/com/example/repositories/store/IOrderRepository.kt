package com.example.repositories.store

import com.example.models.transmissionModels.store.Order
import com.example.models.transmissionModels.store.OrderHistory
import com.example.models.transmissionModels.store.OrderItem
import com.example.models.transmissionModels.store.OrderStatus
import com.example.repositories.Repository

interface IOrderRepository : Repository<Order,Int> {

    fun findByCustomerId(customerId: Int): List<Order?>
    // 根据客户ID查询其所有订单，用于用户查看自己的订单历史。

    fun findOrderItems(orderId: Int): List<OrderItem>
    // 根据订单ID查询订单中的商品项，用于详细展示订单内容。

    fun updateOrderStatus(orderId: Int, status: OrderStatus): Order?
    // 更新订单状态，例如从“支付中”更新为“已支付”，用于订单状态管理。

    fun addOrderHistory(orderId: Int, history: OrderHistory): OrderHistory
    // 添加订单历史记录，用于追踪订单状态变化。
}