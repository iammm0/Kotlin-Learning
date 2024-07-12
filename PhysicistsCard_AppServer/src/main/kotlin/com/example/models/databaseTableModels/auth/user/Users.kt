package com.example.models.databaseTableModels.auth.user

import com.example.models.transmissionModels.auth.user.Role
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