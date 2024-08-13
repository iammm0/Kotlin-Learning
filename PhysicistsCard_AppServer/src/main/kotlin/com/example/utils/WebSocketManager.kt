package com.example.utils

import com.example.models.transmissionModels.community.Message
import com.example.services.dataAccessServices.community.IMessageService
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class WebSocketManager(private val messageService: IMessageService) {
    private val connections = ConcurrentHashMap<String, WebSocketSession>()

    fun connect(userId: String, session: WebSocketSession) {
        connections[userId] = session
    }

    fun disconnect(userId: String) {
        connections.remove(userId)
    }

    suspend fun handleMessage(senderId: String, message: String) {
        val messageData = Json.decodeFromString<Message>(message)

        // 保存消息到数据库
        messageService.saveMessage(messageData)

        // 发送给接收者
        connections[messageData.receiverId]?.send(Frame.Text(Json.encodeToString(messageData)))
    }

    suspend fun handleIncomingFrames(userId: String, session: WebSocketSession) {
        try {
            for (frame in session.incoming) {
                if (frame is Frame.Text) {
                    val receivedText = frame.readText()
                    handleMessage(userId, receivedText)
                }
            }
        } finally {
            disconnect(userId)
        }
    }
}

