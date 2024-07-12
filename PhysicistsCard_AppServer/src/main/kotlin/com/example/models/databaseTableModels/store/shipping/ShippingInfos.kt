package com.example.models.databaseTableModels.store.shipping

import com.example.models.databaseTableModels.store.order.Orders
import com.example.models.transmissionModels.store.shipping.ShippingStatus
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object ShippingInfos : Table("ShippingInfos") {
    val shippingId = varchar("shippingId", 50)
    val orderId = integer("orderId").references(Orders.id)
    val carrier = varchar("carrier", 255)
    val trackingNumber = varchar("trackingNumber", 255)
    val shippingStatus = enumerationByName("shippingStatus", 50, ShippingStatus::class)
    val shippedDate = datetime("shippedDate").nullable()
    val estimatedDeliveryDate = datetime("estimatedDeliveryDate").nullable()
    val actualDeliveryDate = datetime("actualDeliveryDate").nullable()

    override val primaryKey = PrimaryKey(shippingId, name = "PK_ShippingInfos_shippingId")
}