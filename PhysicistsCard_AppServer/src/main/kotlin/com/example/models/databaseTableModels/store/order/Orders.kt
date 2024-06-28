package com.example.models.databaseTableModels.store.order

import com.example.models.transmissionModels.store.OrderStatus
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object Orders : IntIdTable("Orders") {
    val customerId = integer("customerId")
    val status = enumerationByName("status", 50, OrderStatus::class)
    val totalAmount = double("totalAmount")
    val createdAt = datetime("createdAt")
    val updatedAt = datetime("updatedAt").nullable()

    // 使用最新方式确定主键，这里假设是IntIdTable自带的id
}