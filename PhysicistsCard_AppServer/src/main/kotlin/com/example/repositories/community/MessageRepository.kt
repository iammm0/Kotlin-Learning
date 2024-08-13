package com.example.repositories.community

import com.example.models.databaseTableModels.community.interaction.Messages
import com.example.models.transmissionModels.community.Message
import com.example.models.transmissionModels.community.MessageType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class MessageRepository : IMessageRepository {

    override fun saveMessage(message: Message): Boolean {
        return transaction {
            Messages.insert {
                it[messageId] = UUID.randomUUID().toString()
                it[senderId] = message.senderId
                it[receiverId] = message.receiverId
                it[content] = message.content
                it[timestamp] = message.timestamp
                it[messageType] = message.messageType.name
            }.insertedCount > 0
        }
    }

    override fun findMessagesBetween(userId: String, friendId: String): List<Message> {
        return transaction {
            Messages.select {
                (Messages.senderId eq userId and (Messages.receiverId eq friendId)) or
                        (Messages.senderId eq friendId and (Messages.receiverId eq userId))
            }.orderBy(Messages.timestamp to SortOrder.ASC)
                .map {
                    Message(
                        messageId = it[Messages.messageId],
                        senderId = it[Messages.senderId],
                        receiverId = it[Messages.receiverId],
                        content = it[Messages.content],
                        timestamp = it[Messages.timestamp],
                        messageType = MessageType.valueOf(it[Messages.messageType])
                    )
                }
        }
    }
}
