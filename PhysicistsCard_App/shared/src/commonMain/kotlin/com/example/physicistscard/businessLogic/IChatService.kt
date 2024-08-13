package com.example.physicistscard.businessLogic

import com.example.physicistscard.transmissionModels.community.Message
import io.ktor.websocket.WebSocketSession

/**
 * IChatService - 实时聊天系统的业务逻辑接口
 *
 * 该接口定义了与聊天相关的业务逻辑方法。
 */
interface IChatService {

    /**
     * 连接到聊天服务
     * @param userId 用户ID，用于标识连接的用户
     * @return WebSocketSession 对象，用于与聊天服务的通信
     */
    suspend fun connectToChat(userId: String): Result<WebSocketSession>

    /**
     * 发送消息
     * @param session WebSocket会话对象
     * @param message 消息数据模型
     * @return 发送消息的结果，成功或失败
     */
    suspend fun sendMessage(session: WebSocketSession, message: Message): Result<Unit>

    /**
     * 接收消息
     * @param session WebSocket会话对象
     * @return 接收到的消息数据模型
     */
    suspend fun receiveMessage(session: WebSocketSession): Result<Message>
}
