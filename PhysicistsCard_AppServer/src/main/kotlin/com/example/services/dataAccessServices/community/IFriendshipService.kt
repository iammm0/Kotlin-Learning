package com.example.services.dataAccessServices.community

interface IFriendshipService {

    /**
     * 发送好友请求。
     * 用户A向用户B发送好友请求，将请求存储到数据库，并等待用户B接受或拒绝。
     *
     * @param senderId 发送好友请求的用户ID。
     * @param receiverId 接收好友请求的用户ID。
     * @return 如果请求发送成功则返回 true，否则返回 false。
     * @throws IllegalArgumentException 如果发送失败（如用户ID无效或请求已存在）。
     */
    fun sendFriendRequest(senderId: String, receiverId: String): Boolean

    /**
     * 接受好友请求。
     * 用户B接受用户A发送的好友请求，系统将更新好友关系的状态为 ACCEPTED。
     *
     * @param userId 当前用户的ID。
     * @param friendId 好友的用户ID。
     * @return 如果请求接受成功则返回 true，否则返回 false。
     * @throws IllegalArgumentException 如果更新状态失败（如请求不存在或用户ID无效）。
     */
    fun acceptFriendRequest(userId: String, friendId: String): Boolean

    /**
     * 获取好友列表。
     * 返回用户的所有好友列表，只包含状态为 ACCEPTED 的好友关系。
     *
     * @param userId 当前用户的ID。
     * @return 用户的好友ID列表。
     */
    fun getFriends(userId: String): List<String>
}
