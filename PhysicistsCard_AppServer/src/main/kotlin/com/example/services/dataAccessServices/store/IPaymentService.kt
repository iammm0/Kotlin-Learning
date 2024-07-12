package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.payment.PaymentInfo
import com.example.models.transmissionModels.store.payment.PaymentStatus

interface IPaymentService {
    /**
     * 处理订单支付
     *
     * @param orderId 订单ID
     * @param paymentInfo 支付信息
     * @return 支付处理是否成功
     */
    fun processPayment(orderId: String, paymentInfo: PaymentInfo): Boolean

    /**
     * 获取订单支付状态
     *
     * @param orderId 订单ID
     * @return 支付状态
     */
    fun getPaymentStatus(orderId: String): PaymentStatus
}

