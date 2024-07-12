package com.example.physicistscard.transmissionModels.store.order

// 订单状态
enum class OrderStatus {
    AWAITING_PAYMENT, // 等待支付
    PAYING, // 支付中
    PAID, // 已支付
    PROCESSING, // 处理中
    SHIPPED, // 已发货
    DELIVERED, // 已送达
    CANCELLED, // 已取消
    REFUND_REQUESTED, // 退款申请中
    REFUNDING, // 退款中
    REFUNDED, // 退款完成
    RETURNED // 已退货
}