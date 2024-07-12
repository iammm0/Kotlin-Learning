package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.order.Order
import com.example.models.transmissionModels.store.order.OrderStatus

interface IOrderService {
    /**
     * 创建新订单
     *
     * @param order 订单对象
     * @return 新增的订单对象
     */
    fun createOrder(order: Order): Order

    /**
     * 通过订单ID获取订单
     *
     * @param orderId 订单ID
     * @return 订单对象
     */
    fun getOrderById(orderId: String): Order?

    /**
     * 获取所有订单
     *
     * @return 订单列表
     */
    fun getAllOrders(): List<Order?>

    /**
     * 更新订单
     *
     * @param order 订单对象
     * @return 更新后的订单对象
     */
    fun updateOrder(order: Order): Order

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return 删除订单是否成功
     */
    fun deleteOrder(orderId: String): Boolean

    /**
     * 获取用户的所有订单
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    fun getOrdersByUserId(userId: String): List<Order?>

    /**
     * 更改订单状态
     *
     * @param orderId 订单ID
     * @param newStatus 新的订单状态
     * @return 更改订单状态是否成功
     */
    fun changeOrderStatus(orderId: String, newStatus: OrderStatus): Boolean

    /**
     * 获取商家的所有订单
     *
     * @param merchantId 商家ID
     * @return 订单列表
     */
    fun getOrdersByMerchant(merchantId: String): List<Order>
}
