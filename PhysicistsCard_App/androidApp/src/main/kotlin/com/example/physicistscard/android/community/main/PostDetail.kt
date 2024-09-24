package com.example.physicistscard.android.community.main

import UserComment
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.physicistscard.android.ui.screens.HandleResult
import com.example.physicistscard.android.community.main.basicItems.Comment
import com.example.physicistscard.android.viewModels.CommunityViewModel
import kotlinx.datetime.toLocalDateTime
import kotlinx.uuid.UUID

@Composable
fun PostDetail(
    postId: String?,
    communityViewModel: CommunityViewModel = viewModel()
) {
    // 观察添加评论的状态
    val addCommentState by communityViewModel.addCommentState.collectAsState()

    val comments = remember { mutableStateListOf<Comment>() }

    // 当用户提交评论时，调用 ViewModel 方法
    val onCommentSubmitted: (String) -> Unit = { commentText ->
        communityViewModel.addComment(postId!!, UserComment(
            commentId = UUID().toString(),
            userId = "currentUser",
            targetId = postId,
            targetType = CommentTargetType.POST,
            content = commentText,
            createdAt = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
        ))
    }

    Spacer(Modifier.height(36.dp))

    // 处理添加评论的状态
    HandleResult(
        result = addCommentState,
        onSuccess = {
            // 添加成功后，清空输入框，并刷新评论列表
            communityViewModel.getCommentsByTargetId(postId!!, CommentTargetType.POST)
        },
        onLoading = {
            CircularProgressIndicator()
        },
        onError = { error ->
            Text(
                text = "评论失败: ${error.localizedMessage ?: "未知错误"}",
                color = MaterialTheme.colorScheme.error
            )
        }
    )

    PostSection(comments = comments, commentTitle = "推送评论", postId = postId)

    // 使用Card组件包裹内容
    // Card(
    //     modifier = Modifier
    //         .fillMaxWidth()
    //         .padding(2.dp), // 根据需要调整外部填充来控制Card的大小和位置
    //     elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // 根据需要调整卡片的高度
    //     shape = RoundedCornerShape(10.dp),
    //     colors = CardDefaults.cardColors(
    //         containerColor = MaterialTheme.colorScheme.primary // 自定义卡片的背景颜色
    //     )
    // ) {
    //     Column(
    //         modifier = Modifier.padding(16.dp), // 内部填充，控制内容与Card边缘的距离
    //         verticalArrangement = Arrangement.spacedBy(10.dp)
    //     ) {
    //         // 保持PostDetail的原有布局和逻辑
    //         if (post != null) {
    //             Text(
    //                 text = post.title,
    //                 fontWeight = FontWeight.Bold,
    //                 style = MaterialTheme.typography.headlineMedium,
    //                 color = MaterialTheme.colorScheme.secondary
    //             )
    //             Spacer(modifier = Modifier.height(4.dp))
    //             Image(
    //                 painter = painterResource(id = post.imageUrl),
    //                 contentDescription = "Post Image",
    //                 modifier = Modifier
    //                     .fillMaxSize(),
    //                 contentScale = ContentScale.Crop
    //             )
    //             Spacer(modifier = Modifier.height(10.dp))
    //             Text(
    //                 text = post.description,
    //                 color = MaterialTheme.colorScheme.secondary,
    //                 modifier = Modifier.padding(horizontal = 8.dp),
    //                 fontSize = 14.sp
    //             )
    //         }
    //     }
    // }
}