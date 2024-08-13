package com.example.repositories.community

import com.example.models.transmissionModels.community.Message

interface IMessageRepository {

    /**
     * 存储消息。
     * 将消息存储到数据库中，消息内容包括发送者ID、接收者ID、消息文本内容、消息类型和时间戳。
     *
     * @param message 要存储的消息对象。
     * @return 如果消息存储成功则返回 true，否则返回 false。
     */
    fun saveMessage(message: Message): Boolean

    /**
     * 查找用户与好友之间的消息记录。
     * 该方法返回指定用户与好友之间的所有历史消息记录，按时间顺序排列。
     *
     * @param userId 当前用户的ID。
     * @param friendId 好友的用户ID。
     * @return 消息对象的列表，按照时间顺序排列。
     */
    fun findMessagesBetween(userId: String, friendId: String): List<Message>
}

