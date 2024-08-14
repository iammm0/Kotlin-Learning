package com.example.physicistscard.apiServices

import com.example.physicistscard.transmissionModels.auth.user.User

interface IFriendshipApiService {

    /**
     * 发送好友请求
     * @param receiverId 接收好友请求的用户ID
     * @return 操作是否成功
     */
    suspend fun sendFriendRequest(receiverId: String): Boolean

    /**
     * 接受好友请求
     * @param friendId 好友的用户ID
     * @return 操作是否成功
     */
    suspend fun acceptFriendRequest(friendId: String): Boolean

    /**
     * 获取当前用户的好友列表
     * @return 好友列表
     */
    suspend fun getFriends(): List<User>
}
