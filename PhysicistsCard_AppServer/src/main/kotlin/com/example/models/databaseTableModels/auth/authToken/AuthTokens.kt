package com.example.models.databaseTableModels.auth.authToken

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

// object AuthTokens : Table() {
//     val userId = varchar("userId", 50)
//     private val token = varchar("token", 255).uniqueIndex() // 假设每个令牌都是唯一的
//     val expiryDate = timestamp("expiryDate")
//     val issuedAt = timestamp("issuedAt")
//
//     override val primaryKey = PrimaryKey(token, name = "PK_AuthTokens_Token") // 将令牌作为主键
// }