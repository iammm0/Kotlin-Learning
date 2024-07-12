package com.example.physicistscard.transmissionModels.store.order

// 订单内商品详情
data class OrderItem(
    val itemId: Int,
    val orderId: Int,
    val productId: Int,
    val productName: String,
    val productImage: String, // 产品图片URL，从产品模型中继承
    val quantity: Int,
    val pricePerItem: Double,
    val productSpec: String? // 产品规格，例如“牛顿版”，“爱因斯坦版”
)