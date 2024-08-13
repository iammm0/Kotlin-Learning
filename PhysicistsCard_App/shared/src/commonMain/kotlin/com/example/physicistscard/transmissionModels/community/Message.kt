package com.example.physicistscard.transmissionModels.community

import com.example.physicistscard.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val messageId: String,  // 消息唯一标识符
    val senderId: String,   // 发送者的用户ID
    val receiverId: String, // 接收者的用户ID
    val content: String,    // 消息内容
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime, // 消息发送时间
    val messageType: MessageType // 消息类型（文本、图片、视频等）
)
