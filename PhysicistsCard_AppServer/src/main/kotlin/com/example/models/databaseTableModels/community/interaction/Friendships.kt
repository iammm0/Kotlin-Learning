package com.example.models.databaseTableModels.community.interaction

import com.example.models.databaseTableModels.auth.user.Users
import org.jetbrains.exposed.sql.Table

object Friendships : Table() {
    val userId = varchar("userId", 50).references(Users.userId)
    val friendId = varchar("friendId", 50).references(Users.userId)
    val status = varchar("status", 20) // "PENDING", "ACCEPTED", "REJECTED"

    override val primaryKey = PrimaryKey(userId, friendId, name = "PK_Friendships")
}
