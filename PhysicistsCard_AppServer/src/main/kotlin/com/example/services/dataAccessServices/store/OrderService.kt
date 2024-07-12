package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.order.Order
import com.example.models.transmissionModels.store.order.OrderStatus
import com.example.repositories.store.IOrderRepository
import com.example.repositories.store.OrderRepository

class OrderService(private val orderRepository: IOrderRepository) : IOrderService {

    override fun createOrder(order: Order): Order {
        // 这里可能需要在创建订单前进行一些业务逻辑检查
        // 例如：检查库存、计算价格等
        return orderRepository.add(order)
    }

    override fun getOrderById(orderId: String): Order? {
        return orderRepository.findById(orderId.toInt())  // 假设 orderId 可以安全转换为 Int
    }

    override fun getAllOrders(): List<Order?> {
        return orderRepository.findAll()
    }

    override fun updateOrder(order: Order): Order {
        // 更新订单前可能需要进行的业务逻辑检查
        // 比如：只允许在特定状态下修改订单
        return orderRepository.update(order)
    }

    override fun deleteOrder(orderId: String): Boolean {
        // 删除订单前可能需要检查订单状态，确保可以安全删除
        return orderRepository.delete(orderId.toInt())  // 同样假设 orderId 可以安全转换为 Int
    }

    override fun getOrdersByUserId(userId: String): List<Order?> {
        // 这里我们需要一个方法来根据用户 ID 查找订单，可能需要在 Repository 中实现
        return orderRepository.findByCustomerId(userId.toInt())  // 假设 userId 可以安全转换为 Int
    }

    override fun changeOrderStatus(orderId: String, newStatus: OrderStatus): Boolean {
        // 改变订单状态可能需要更复杂的业务逻辑处理，例如发送通知或更新库存
        return orderRepository.updateOrderStatus(orderId.toInt(), newStatus) != null
    }

    override fun getOrdersByMerchant(merchantId: String): List<Order> {
        TODO("Not yet implemented")
    }
}
