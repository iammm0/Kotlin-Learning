package com.example.models.databaseTableModels.store.order

import com.example.models.transmissionModels.store.order.OrderStatus
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object OrderHistories : Table("OrderHistories") {
    val historyId = varchar("historyId", 50)  // 使用 varchar 类型的主键
    val orderId = integer("orderId").references(Orders.id)  // 外键，引用 Orders 表的 id 字段
    val status = enumerationByName("status", 50, OrderStatus::class)  // 订单状态，使用枚举类型存储
    val changeReason = varchar("changeReason", 255).nullable()  // 可为空的变更原因字段
    val changedAt = datetime("changedAt")  // 变更时间，使用 datetime 类型存储

    override val primaryKey = PrimaryKey(OrderItems.orderId, name = "PK_OrderHistories_historyId")
}