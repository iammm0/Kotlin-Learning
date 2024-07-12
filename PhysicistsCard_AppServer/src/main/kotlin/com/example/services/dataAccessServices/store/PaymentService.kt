package com.example.services.dataAccessServices.store

import com.example.models.transmissionModels.store.payment.PaymentInfo
import com.example.models.transmissionModels.store.payment.PaymentStatus

class PaymentService : IPaymentService {
    override fun processPayment(orderId: String, paymentInfo: PaymentInfo): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPaymentStatus(orderId: String): PaymentStatus {
        TODO("Not yet implemented")
    }
}