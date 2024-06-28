package com.example.repositories.store

import com.example.models.transmissionModels.store.PaymentInfo
import com.example.models.transmissionModels.store.PaymentStatus
import com.example.repositories.Repository

interface IPaymentInfoRepository : Repository<PaymentInfo,String> {
    fun findByOrderId(orderId: Int): PaymentInfo?
    // 根据订单ID查询支付信息，用于检查订单支付状态或记录支付详情

    fun updatePaymentStatus(paymentId: String, status: PaymentStatus): PaymentInfo?
    // 更新支付状态，例如从“待支付”更新为“已完成”，用于支付状态管理。
}