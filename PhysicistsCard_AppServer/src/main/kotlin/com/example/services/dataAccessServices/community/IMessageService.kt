package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.Message

interface IMessageService {

    /**
     * 存储消息。
     * 将发送者发送的消息存储到数据库中，以便后续检索或发送给接收者。
     *
     * @param message 要存储的消息对象。
     * @return 如果消息存储成功则返回 true，否则返回 false。
     * @throws IllegalArgumentException 如果存储失败（如消息内容无效）。
     */
    fun saveMessage(message: Message): Boolean

    /**
     * 获取用户与好友之间的消息记录。
     * 返回当前用户与指定好友之间的所有历史消息。
     *
     * @param userId 当前用户的ID。
     * @param friendId 好友的用户ID。
     * @return 消息对象的列表，按时间顺序排列。
     */
    fun getMessages(userId: String, friendId: String): List<Message>
}

