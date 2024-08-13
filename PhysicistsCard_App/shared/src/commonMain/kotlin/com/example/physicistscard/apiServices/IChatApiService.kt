package com.example.physicistscard.apiServices

import com.example.physicistscard.transmissionModels.community.Message
import io.ktor.websocket.*

/**
 * 实时聊天系统的API服务接口，定义了与聊天相关的操作。
 */
interface IChatApiService {

    /**
     * 连接到聊天 WebSocket。
     *
     * @param userId 用户ID，用于标识连接的用户。
     * @return WebSocketSession 用于与服务端的实时通信。
     */
    suspend fun connectToChat(userId: String): WebSocketSession

    /**
     * 发送消息，通过 WebSocket 发送消息给特定用户。
     *
     * @param session WebSocket会话对象。
     * @param message 消息数据模型，包含发送者、接收者、消息内容等信息。
     */
    suspend fun sendMessage(session: WebSocketSession, message: Message)

    /**
     * 接收消息，通过 WebSocket 接收消息。
     *
     * @param session WebSocket会话对象。
     * @return 接收到的消息数据模型。
     */
    suspend fun receiveMessage(session: WebSocketSession): Message
}
