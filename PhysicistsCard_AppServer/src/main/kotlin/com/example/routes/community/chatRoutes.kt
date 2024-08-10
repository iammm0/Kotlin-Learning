package com.example.routes


import com.example.models.transmissionModels.community.Message
import com.example.models.transmissionModels.community.MessageType
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.ConcurrentHashMap

fun Application.chatRoutes() {
    val connections = Collections.newSetFromMap(ConcurrentHashMap<DefaultWebSocketServerSession, Boolean>())

    routing {
        route("/community/chat") {
            webSocket {
                val session = this

                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val receivedText = frame.readText()
                            println("Received: $receivedText")

                            try {
                                val message = Json.decodeFromString<Message>(receivedText)
                                println("Message from ${message.senderId} to ${message.receiverId}: ${message.content}")

                                // 创建响应消息
                                val responseMessage = Message(
                                    messageId = "server-generated-id",
                                    senderId = "server",
                                    receiverId = message.senderId,
                                    content = "Echo: ${message.content}",
                                    timestamp = LocalDateTime.now(ZoneId.systemDefault()),
                                    messageType = MessageType.TEXT
                                )

                                // 发送响应消息给客户端
                                session.send(Frame.Text(Json.encodeToString(responseMessage)))
                            } catch (e: Exception) {
                                println("Failed to parse message: $e")
                            }
                        }
                        is Frame.Binary -> {
                            // 处理二进制数据
                        }
                        is Frame.Ping -> {
                            // 处理 Ping
                        }
                        is Frame.Pong -> {
                            // 处理 Pong
                        }
                        is Frame.Close -> {
                            // 处理关闭请求
                            println("Client disconnected")
                        }
                    }
                }
            }
        }
    }
}