package com.example.models.databaseTableModels.store.payment

import com.example.models.databaseTableModels.store.order.Orders
import com.example.models.transmissionModels.store.PaymentMethod
import com.example.models.transmissionModels.store.PaymentStatus
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object PaymentInfos : Table("PaymentInfos") {
    val paymentId = varchar("paymentId", 50)
    val orderId = integer("orderId").references(Orders.id)
    val paymentMethod = enumerationByName("paymentMethod", 50, PaymentMethod::class)
    val amountPaid = double("amountPaid")
    val paymentStatus = enumerationByName("paymentStatus", 50, PaymentStatus::class)
    val paymentDate = datetime("paymentDate")
    val transactionId = varchar("transactionId", 255).nullable()

    override val primaryKey = PrimaryKey(paymentId, name = "PK_PaymentInfos_paymentId")
}