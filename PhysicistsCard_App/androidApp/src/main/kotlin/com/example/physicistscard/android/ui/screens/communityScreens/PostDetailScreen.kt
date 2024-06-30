package com.example.physicistscard.android.ui.screens.communityScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.screens.communityScreens.basicItems.Comment

val commentsSample = listOf(
    Comment(userId = "1", username = "赵明俊", commentText = "我在今天将过去的想法付诸于实践。", timestamp = "2024-02-27"),
    Comment(userId = "2", username = "赵明俊", commentText = "我在今天提出了这个想法。", timestamp = "2024-01-06"),
    Comment(userId = "3", username = "李培梁", commentText = "尊重事实，追求真理" , timestamp = "2024-01-27"),
    Comment(userId = "4", username = "张子洋", commentText = "学霸能教我谈恋爱吗？", timestamp = "2023-12-29"),
    Comment(userId = "5", username = "离开有你的盛夏", commentText = "折磨我的不是离别，而是一次又一次的回忆。", timestamp = "2024-02-27"),
    Comment(userId = "6", username = "我是明明啊", commentText = "天下万般兵刃，唯有过往伤人最深。", timestamp = "2019-02-17"),
    Comment(userId = "8", username = "侯兴旺", commentText = "结局可知那是电影，结局未知才是人生", timestamp = "2024-02-28"),
    Comment(userId = "9", username = "漆星", commentText = "我被量子力学搞懵了", timestamp = "2024-03-1"),
    Comment(userId = "10", username = "不便秘不窜稀", commentText = "买了卡牌会肠胃健康吗？", timestamp = "2024-02-28"),
    Comment(userId = "11", username = "谢紫萱", commentText = "物理不及格的我觉得太魔幻了", timestamp = "2024-02-28"),
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostDetailScreen(postId: String?, comments: List<Comment>, navController: NavController) {
    Column {
        PostSection(comments = comments, commentTitle = "推送评论", postId = postId)
    }
}

