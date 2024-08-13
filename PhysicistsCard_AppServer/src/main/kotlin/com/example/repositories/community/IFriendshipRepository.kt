package com.example.repositories.community

interface IFriendshipRepository {

    /**
     * 创建好友请求。
     * 该方法将发送方和接收方的用户ID存储到好友关系表中，并将状态设置为 PENDING。
     *
     * @param senderId 发送好友请求的用户ID。
     * @param receiverId 接收好友请求的用户ID。
     * @return 如果好友请求创建成功则返回 true，否则返回 false。
     */
    fun createFriendRequest(senderId: String, receiverId: String): Boolean

    /**
     * 更新好友请求的状态。
     * 该方法用于将好友请求的状态更新为 ACCEPTED、REJECTED 或其他自定义状态。
     *
     * @param userId 当前用户的ID。
     * @param friendId 好友的用户ID。
     * @param status 新的好友关系状态。
     * @return 如果状态更新成功则返回 true，否则返回 false。
     */
    fun updateFriendRequestStatus(userId: String, friendId: String, status: String): Boolean

    /**
     * 查找用户的好友列表。
     * 该方法返回用户的所有好友的ID列表，状态为 ACCEPTED 的好友关系会被返回。
     *
     * @param userId 用户的唯一标识符。
     * @return 好友的用户ID列表。
     */
    fun findFriends(userId: String): List<String>
}
