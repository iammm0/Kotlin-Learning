package com.example.physicistscard.android.community.main

import LikeTargetType
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.screens.HandleResult
import com.example.physicistscard.android.ui.components.layouts.CommunityTopAppBar
import com.example.physicistscard.android.community.main.basicItems.PostList
import com.example.physicistscard.android.viewModels.CommunityViewModel

@Composable
fun CommunityScreen(
    navController: NavController,
    communityViewModel: CommunityViewModel = viewModel()
) {
    // 观察 ViewModel 中的状态
    val allPostsState by communityViewModel.allPostsState.collectAsState()

    // 在 UI 初始化时调用 ViewModel 的方法加载数据
    LaunchedEffect(Unit) {
        communityViewModel.getAllPosts()
    }

    Column {
        CommunityTopAppBar(text = "社区", navController = navController) {}

        HandleResult(
            result = allPostsState,
            onSuccess = { posts ->
                PostList(
                    posts = posts.map { it.toPost() }, // 假设有一个方法将数据模型转换成 UI 模型
                    navController = navController,
                    onLikeIncremented = { postId, newLikes ->
                        communityViewModel.addLike("userId", postId, LikeTargetType.POST)
                    },
                    onDismissRequest = {
                        // 处理评论输入栏隐藏逻辑
                    }
                )
            },
            onLoading = {
                CircularProgressIndicator()
            },
            onError = { error ->
                Text(
                    text = "加载失败: ${error.localizedMessage ?: "未知错误"}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}