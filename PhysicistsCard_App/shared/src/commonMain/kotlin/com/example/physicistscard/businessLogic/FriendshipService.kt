package com.example.physicistscard.businessLogic

import com.example.physicistscard.apiServices.IFriendshipApiService
import com.example.physicistscard.transmissionModels.auth.user.User

class FriendshipService(
    private val friendshipApiService: IFriendshipApiService
) : IFriendshipService {

    override suspend fun sendFriendRequest(receiverId: String): Result<Boolean> {
        return try {
            val success = friendshipApiService.sendFriendRequest(receiverId)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun acceptFriendRequest(friendId: String): Result<Boolean> {
        return try {
            val success = friendshipApiService.acceptFriendRequest(friendId)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFriends(): Result<List<User>> {
        return try {
            val friends = friendshipApiService.getFriends()
            Result.success(friends)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
