package com.example.models.transmissionModels.auth

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime

object Users : Table() {
    val userId = varchar("userId", 50)
    val username = varchar("username", 255)
    val email = varchar("email", 255).nullable()
    val phone = varchar("phone", 20).nullable()
    val passwordHash = varchar("passwordHash", 255)
    val avatarUrl = varchar("avatarUrl", 500).nullable()
    val bio = text("bio").nullable()
    val registerDate = datetime("registerDate")
    val isEmailVerified = bool("isEmailVerified")
    val isPhoneVerified = bool("isPhoneVerified")
    val role = enumerationByName("role", 255, Role::class) // 使用枚举类型

    // 明确指定主键
    override val primaryKey = PrimaryKey(userId, name = "PK_Users_userId")
}


// object UserAddresses : Table() {
//     private val addressId = varchar("addressId", 50)
//     val userId = varchar("userId", 50) references Users.userId
//     val recipientName = varchar("recipientName", 255)
//     val phoneNumber = varchar("phoneNumber", 20)
//     val province = varchar("province", 100)
//     val city = varchar("city", 100)
//     val district = varchar("district", 100)
//     val street = varchar("street", 255)
//     val residentialCommunity = varchar("residentialCommunity", 255).nullable()
//     val buildingNumber = varchar("buildingNumber", 50).nullable()
//     val unitNumber = varchar("unitNumber", 50).nullable()
//     val roomNumber = varchar("roomNumber", 50).nullable()
//     val zipCode = varchar("zipCode", 20)
//
//     // 明确指定主键
//     override val primaryKey = PrimaryKey(addressId, name = "PK_UserAddresses_addressId")
// }
