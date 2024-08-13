package com.example.models.databaseTableModels.community.interaction

import com.example.models.databaseTableModels.auth.user.Users
import com.example.models.databaseTableModels.community.interaction.favorite.UserFavorites.favoriteId
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Messages : Table() {
    val messageId = varchar("messageId", 50)
    val senderId = varchar("senderId", 50).references(Users.userId)
    val receiverId = varchar("receiverId", 50).references(Users.userId)
    val content = text("content")
    val timestamp = datetime("timestamp")
    val messageType = varchar("messageType", 50)

    override val primaryKey = PrimaryKey(messageId, name = "PK_Messages_messageId")
}
