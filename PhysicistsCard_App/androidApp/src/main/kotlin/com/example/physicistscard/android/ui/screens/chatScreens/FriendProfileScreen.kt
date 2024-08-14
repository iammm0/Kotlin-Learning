package com.example.physicistscard.android.ui.screens.chatScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar
import com.example.physicistscard.android.viewModels.FriendshipViewModel
import com.example.physicistscard.android.viewModels.UserViewModel

@Composable
fun FriendProfileScreen(
    navController: NavController,
    friendId: String?,
    userViewModel: UserViewModel = viewModel()
) {
    // 获取用户信息状态
    val userInfoState by userViewModel.userInfoState.collectAsState()

    // 如果有 friendId，加载好友信息
    friendId?.let {
        userViewModel.getUserInfo(it)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        when {
            userInfoState == null -> {
                Text("正在加载...", style = MaterialTheme.typography.bodyLarge)
            }
            userInfoState?.isSuccess == true -> {
                val friend = userInfoState?.getOrNull()
                friend?.let {
                    Text(text = "${friend.username} 的个人信息", style = MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "用户名: ${friend.username}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "邮箱: ${friend.email ?: "无"}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "电话: ${friend.phone ?: "无"}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "简介: ${friend.bio ?: "无"}", style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        Button(onClick = { /* Handle add friend */ }) {
                            Text("添加好友")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = { /* Handle delete friend */ }) {
                            Text("删除好友")
                        }
                    }
                }
            }
            userInfoState?.isFailure == true -> {
                val exception = userInfoState?.exceptionOrNull()
                Text(
                    text = "加载失败: ${exception?.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

