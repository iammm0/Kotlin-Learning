package com.example.physicistscard.android.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicistscard.businessLogic.IFriendshipService
import com.example.physicistscard.transmissionModels.auth.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FriendshipViewModel(
    private val friendshipService: IFriendshipService
) : ViewModel() {

    // 好友请求发送状态
    private val _sendFriendRequestState = MutableStateFlow<Result<Boolean>?>(null)
    val sendFriendRequestState: StateFlow<Result<Boolean>?> = _sendFriendRequestState

    // 好友请求接受状态
    private val _acceptFriendRequestState = MutableStateFlow<Result<Boolean>?>(null)
    val acceptFriendRequestState: StateFlow<Result<Boolean>?> = _acceptFriendRequestState

    // 获取好友列表状态
    private val _friends = MutableStateFlow<Result<List<User>>?>(null)
    val friends: StateFlow<Result<List<User>>?> = _friends

    // 发送好友请求
    fun sendFriendRequest(receiverId: String) {
        viewModelScope.launch {
            _sendFriendRequestState.value = try {
                val result = friendshipService.sendFriendRequest(receiverId)
                Result.success(result.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 接受好友请求
    fun acceptFriendRequest(friendId: String) {
        viewModelScope.launch {
            _acceptFriendRequestState.value = try {
                val result = friendshipService.acceptFriendRequest(friendId)
                Result.success(result.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 加载好友列表
    fun loadFriends() {
        viewModelScope.launch {
            _friends.value = try {
                val friendsList = friendshipService.getFriends()
                Result.success(friendsList.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 清除状态方法
    fun clearSendFriendRequestState() {
        _sendFriendRequestState.value = null
    }

    fun clearAcceptFriendRequestState() {
        _acceptFriendRequestState.value = null
    }

    fun clearFriendsState() {
        _friends.value = null
    }
}
