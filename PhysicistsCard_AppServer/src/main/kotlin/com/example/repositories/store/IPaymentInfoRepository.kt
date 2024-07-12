package com.example.repositories.store

import com.example.models.transmissionModels.store.payment.PaymentInfo
import com.example.models.transmissionModels.store.payment.PaymentStatus
import com.example.repositories.Repository

interface IPaymentInfoRepository : Repository<PaymentInfo, String> {
    /**
     * 根据订单ID查询支付信息
     *
     * @param orderId 订单ID
     * @return 支付信息对象，或null表示未找到
     */
    fun findByOrderId(orderId: Int): PaymentInfo?

    /**
     * 更新支付状态
     *
     * @param paymentId 支付ID
     * @param status 新的支付状态
     * @return 更新后的支付信息对象，或null表示更新失败
     */
    fun updatePaymentStatus(paymentId: String, status: PaymentStatus): PaymentInfo?
}
