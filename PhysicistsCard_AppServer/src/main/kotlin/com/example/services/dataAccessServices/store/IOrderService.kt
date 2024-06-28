package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.Order
import com.example.models.transmissionModels.store.OrderStatus

interface IOrderService {
    fun createOrder(order: Order): Order
    fun getOrderById(orderId: String): Order?
    fun getAllOrders(): List<Order?>
    fun updateOrder(order: Order): Order
    fun deleteOrder(orderId: String): Boolean
    fun getOrdersByUserId(userId: String): List<Order?>
    fun changeOrderStatus(orderId: String, newStatus: OrderStatus): Boolean
}
