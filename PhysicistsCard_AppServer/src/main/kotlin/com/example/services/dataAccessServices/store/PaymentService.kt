package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.payment.PaymentInfo
import com.example.models.transmissionModels.store.payment.PaymentStatus
import com.example.repositories.store.IPaymentInfoRepository

class PaymentService(private val paymentInfoRepository: IPaymentInfoRepository) : IPaymentService {

    override fun processPayment(orderId: String, paymentInfo: PaymentInfo): Boolean {
        paymentInfoRepository.add(paymentInfo)
        return true
    }

    override fun getPaymentStatus(orderId: String): PaymentStatus {
        val paymentInfo = paymentInfoRepository.findByOrderId(orderId.toInt())
        return paymentInfo?.paymentStatus ?: PaymentStatus.FAILED
    }
}
