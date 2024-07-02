package com.example.models.databaseTableModels.auth.user

import org.jetbrains.exposed.sql.Table


object UserAddresses : Table() {
    private val addressId = varchar("addressId", 50)
    val userId = varchar("userId", 50) references Users.userId
    val recipientName = varchar("recipientName", 255)
    val phoneNumber = varchar("phoneNumber", 20)
    val province = varchar("province", 100)
    val city = varchar("city", 100)
    val district = varchar("district", 100)
    val street = varchar("street", 255)
    val residentialCommunity = varchar("residentialCommunity", 255).nullable()
    val buildingNumber = varchar("buildingNumber", 50).nullable()
    val unitNumber = varchar("unitNumber", 50).nullable()
    val roomNumber = varchar("roomNumber", 50).nullable()
    val zipCode = varchar("zipCode", 20)

    // 明确指定主键
    override val primaryKey = PrimaryKey(addressId, name = "PK_UserAddresses_addressId")
}
