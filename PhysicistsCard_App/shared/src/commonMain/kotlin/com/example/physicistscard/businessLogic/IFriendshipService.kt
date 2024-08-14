package com.example.physicistscard.businessLogic

import com.example.physicistscard.transmissionModels.auth.user.User

interface IFriendshipService {

    /**
     * 发送好友请求
     * @param receiverId 接收者的用户ID
     * @return 操作是否成功
     */
    suspend fun sendFriendRequest(receiverId: String): Result<Boolean>

    /**
     * 接受好友请求
     * @param friendId 好友的用户ID
     * @return 操作是否成功
     */
    suspend fun acceptFriendRequest(friendId: String): Result<Boolean>

    /**
     * 获取当前用户的好友列表
     * @return 好友列表
     */
    suspend fun getFriends(): Result<List<User>>
}
