package com.example.physicistscard.android.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicistscard.businessLogic.IUserService
import com.example.physicistscard.transmissionModels.auth.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userService: IUserService
) : ViewModel() {

    private val _userInfoState = MutableStateFlow<Result<User?>?>(null)
    val userInfoState: StateFlow<Result<User?>?> = _userInfoState

    // 获取用户信息
    fun getUserInfo(targetUserId: String) {
        viewModelScope.launch {
            _userInfoState.value = userService.getUserInfo(targetUserId)
        }
    }

    // 清除用户信息状态
    fun clearUserInfoState() {
        _userInfoState.value = null
    }
}
