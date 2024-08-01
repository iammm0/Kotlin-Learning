package com.example.models.databaseTableModels.auth.authToken

import com.example.models.databaseTableModels.auth.user.Users
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp

object RefreshTokens : Table("RefreshTokens") {
    val tokenId = varchar("tokenId", 50)
    val userId = varchar("userId", 50).references(Users.userId)
    val token = varchar("token", 255).uniqueIndex() // 假设每个令牌都是唯一的
    val expiryDate = datetime("expiryDate")
    val issuedAt = datetime("issuedAt")

    override val primaryKey = PrimaryKey(tokenId, name = "PK_RefreshToken_ID") // 将令牌作为主键
}