package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.Message
import com.example.repositories.community.IMessageRepository

class MessageService(
    private val messageRepository: IMessageRepository
) : IMessageService {

    override fun saveMessage(message: Message): Boolean {
        return messageRepository.saveMessage(message)
    }

    override fun getMessages(userId: String, friendId: String): List<Message> {
        return messageRepository.findMessagesBetween(userId, friendId)
    }
}
