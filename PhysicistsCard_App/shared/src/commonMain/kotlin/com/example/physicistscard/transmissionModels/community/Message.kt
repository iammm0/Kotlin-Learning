package com.example.physicistscard.transmissionModels.community

import com.example.physicistscard.transmissionModels.auth.user.User
import com.example.physicistscard.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val messageId: String, // 消息唯一标识符
    val sender: User, // 发送者信息
    val receiver: User, // 接收者信息
    val content: String, // 消息内容
    val messageType: MessageType, // 消息类型（文本、图片、视频等）
    @Serializable(with = LocalDateTimeSerializer::class)
    val timestamp: LocalDateTime, // 消息发送时间
    val isRead: Boolean = false // 消息是否已读
)
