package com.example.repositories.store

import com.example.models.transmissionModels.store.order.Order
import com.example.models.transmissionModels.store.order.OrderHistory
import com.example.models.transmissionModels.store.order.OrderItem
import com.example.models.transmissionModels.store.order.OrderStatus
import com.example.repositories.Repository

interface IOrderRepository : Repository<Order, Int> {
    /**
     * 根据客户ID查询其所有订单
     *
     * @param customerId 客户ID
     * @return 订单列表
     */
    fun findByCustomerId(customerId: Int): List<Order?>

    /**
     * 根据订单ID查询订单中的商品项
     *
     * @param orderId 订单ID
     * @return 订单商品项列表
     */
    fun findOrderItems(orderId: Int): List<OrderItem>

    /**
     * 更新订单状态
     *
     * @param orderId 订单ID
     * @param status 新的订单状态
     * @return 更新后的订单对象，或null表示更新失败
     */
    fun updateOrderStatus(orderId: Int, status: OrderStatus): Order?

    /**
     * 添加订单历史记录
     *
     * @param orderId 订单ID
     * @param history 订单历史记录
     * @return 添加的订单历史记录对象
     */
    fun addOrderHistory(orderId: Int, history: OrderHistory): OrderHistory
}
