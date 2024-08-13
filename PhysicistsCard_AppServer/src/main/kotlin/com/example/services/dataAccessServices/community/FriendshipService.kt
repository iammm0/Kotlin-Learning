package com.example.services.dataAccessServices.community

import com.example.repositories.community.IFriendshipRepository

class FriendshipService(
    private val friendshipRepository: IFriendshipRepository
) : IFriendshipService {

    override fun sendFriendRequest(senderId: String, receiverId: String): Boolean {
        return friendshipRepository.createFriendRequest(senderId, receiverId)
    }

    override fun acceptFriendRequest(userId: String, friendId: String): Boolean {
        // 在接受请求之前，可以检查请求是否存在，并确认用户有权接受
        return friendshipRepository.updateFriendRequestStatus(friendId, userId, "ACCEPTED")
    }

    override fun getFriends(userId: String): List<String> {
        return friendshipRepository.findFriends(userId)
    }
}
