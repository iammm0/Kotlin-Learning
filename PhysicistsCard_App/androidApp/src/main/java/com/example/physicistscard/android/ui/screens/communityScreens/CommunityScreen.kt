package com.example.physicistscard.android.ui.screens.communityScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.physicistscard.android.R
import com.example.physicistscard.android.ui.components.layouts.CommunityTopAppBar
import com.example.physicistscard.android.ui.screens.communityScreens.basicItems.Post
import com.example.physicistscard.android.ui.screens.communityScreens.basicItems.PostList

// 示例帖子数据列表
val communityPosts = listOf(
    Post(postId = "1",
        imageUrl = R.drawable.newton,
        title = "牛顿",
        description = "如果说我看得远，那是因为我站在巨人的肩膀上。",),
    Post(postId = "2",
        imageUrl = R.drawable.albert,
        title = "爱因斯坦",
        description = "科学不过是把日常所思加以精炼。"),
    Post(postId = "3",
        imageUrl = R.drawable.maxwell,
        title = "麦克斯韦",
        description = "我们无法预测未来，但我们可以创造它。"),
    Post(
        postId = "5",
        imageUrl = R.drawable.curie,
        title = "居里",
        description = "科学家需要的是幻想力和直觉。"
    ),
    Post(
        postId = "8",
        imageUrl = R.drawable.bohr,
        title = "玻尔",
        description = "一个伟大的真理的标志是，其反面也是一个真理。"
    ),
    Post(
        postId = "11",
        imageUrl = R.drawable.galileo, // 请替换为实际的资源ID
        title = "伽利略",
        description = "测量那些可被测量的，使那些不可被测量的可被测量。"
    ),
)

// 实现onLikeIncremented回调
val onLikeIncremented: (String, Int) -> Unit = { postId, newLikes ->
    // 例如，打印被点赞的帖子ID和新的点赞数
    println("Post ID: $postId, New Likes: $newLikes")
    // TODO: 在这里添加更新后端数据库的逻辑或进行其他操作
}

// 处理评论输入栏隐藏的逻辑
val onDismissRequest: () -> Unit = {
    // 这里可以处理隐藏评论输入栏的逻辑
    println("评论输入栏已隐藏")
}

@Composable
fun CommunityScreen(navController: NavController) {
    Column {
        CommunityTopAppBar(text = "社区", navController = navController) {}
        PostList(
            posts = communityPosts,
            navController,
            onLikeIncremented = onLikeIncremented,
            onDismissRequest =  onDismissRequest
        )
    }
}