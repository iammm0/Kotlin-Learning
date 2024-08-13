package com.example.physicistscard.apiServices

import com.example.physicistscard.transmissionModels.community.Message
import com.example.physicistscard.utils.receiveDeserialized
import com.example.physicistscard.utils.sendSerialized
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.websocket.WebSocketSession

class ChatApiService(private val client: HttpClient) : IChatApiService {

    override suspend fun connectToChat(userId: String): WebSocketSession {
        return client.webSocketSession {
            url("${NetworkConfig.BASE_URL}/chat")
            parameter("userId", userId)
        }
    }

    override suspend fun sendMessage(session: WebSocketSession, message: Message) {
        session.sendSerialized(message)
    }

    override suspend fun receiveMessage(session: WebSocketSession): Message {
        return session.receiveDeserialized()
    }
}