package com.example.physicistscard.businessLogic

import com.example.physicistscard.apiServices.IChatApiService
import com.example.physicistscard.transmissionModels.community.Message
import io.ktor.websocket.WebSocketSession

class ChatService(
    private val chatApiService: IChatApiService
) : IChatService {

    override suspend fun connectToChat(userId: String): Result<WebSocketSession> {
        return try {
            val session = chatApiService.connectToChat(userId)
            Result.success(session)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendMessage(session: WebSocketSession, message: Message): Result<Unit> {
        return try {
            chatApiService.sendMessage(session, message)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun receiveMessage(session: WebSocketSession): Result<Message> {
        return try {
            val message = chatApiService.receiveMessage(session)
            Result.success(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
