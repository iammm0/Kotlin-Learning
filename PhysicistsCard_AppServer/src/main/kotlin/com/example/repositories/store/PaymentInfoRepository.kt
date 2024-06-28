package com.example.repositories.store

import com.example.models.transmissionModels.store.PaymentInfo
import com.example.models.transmissionModels.store.PaymentStatus
import com.example.models.databaseTableModels.store.payment.PaymentInfos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class PaymentInfoRepository : IPaymentInfoRepository {

    override fun add(item: PaymentInfo): PaymentInfo = transaction {
        PaymentInfos.insert {
            it[paymentId] = item.paymentId
            it[orderId] = item.orderId.toInt()  // 假设orderId是整型
            it[paymentMethod] = item.paymentMethod
            it[amountPaid] = item.amountPaid
            it[paymentStatus] = item.paymentStatus
            it[paymentDate] = item.paymentDate
            it[transactionId] = item.transactionId
        }
        item  // 返回插入的对象
    }

    override fun findById(id: String): PaymentInfo? = transaction {
        PaymentInfos.selectAll().where { PaymentInfos.paymentId eq id }
            .mapNotNull { it.toPaymentInfo() }
            .singleOrNull()  // 确保只有一个结果返回
    }

    override fun findByOrderId(orderId: Int): PaymentInfo? = transaction {
        PaymentInfos.selectAll().where { PaymentInfos.orderId eq orderId }
            .mapNotNull { it.toPaymentInfo() }
            .singleOrNull()
    }

    override fun updatePaymentStatus(paymentId: String, status: PaymentStatus): PaymentInfo? = transaction {
        PaymentInfos.update({ PaymentInfos.paymentId eq paymentId }) {
            it[paymentStatus] = status
        }
        findById(paymentId)  // 更新状态后返回更新的对象
    }

    override fun update(item: PaymentInfo): PaymentInfo = transaction {
        PaymentInfos.update({ PaymentInfos.paymentId eq item.paymentId }) {
            it[paymentMethod] = item.paymentMethod
            it[amountPaid] = item.amountPaid
            it[paymentStatus] = item.paymentStatus
            it[paymentDate] = item.paymentDate
            it[transactionId] = item.transactionId
        }
        findById(item.paymentId)!!  // 确认更新后返回
    }

    override fun findAll(): List<PaymentInfo?> = transaction {
        PaymentInfos.selectAll().map { it.toPaymentInfo() }
    }

    override fun delete(id: String): Boolean = transaction {
        PaymentInfos.deleteWhere { paymentId eq id } > 0  // 删除指定ID的记录，并返回是否成功
    }

    private fun ResultRow.toPaymentInfo() = PaymentInfo(
        paymentId = this[PaymentInfos.paymentId],
        orderId = this[PaymentInfos.orderId].toString(),  // 将Int转为String
        paymentMethod = this[PaymentInfos.paymentMethod],
        amountPaid = this[PaymentInfos.amountPaid],
        paymentStatus = this[PaymentInfos.paymentStatus],
        paymentDate = this[PaymentInfos.paymentDate],
        transactionId = this[PaymentInfos.transactionId]
    )
}
