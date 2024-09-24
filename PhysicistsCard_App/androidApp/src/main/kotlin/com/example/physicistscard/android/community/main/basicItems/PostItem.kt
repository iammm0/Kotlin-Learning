package com.example.physicistscard.android.community.main.basicItems

import Post
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.physicistscard.android.viewModels.CommunityViewModel

@Composable
fun PostItem(
    post: Post,
    navController: NavController,
    communityViewModel: CommunityViewModel = viewModel()
) {

    val likeState by communityViewModel.likeState.collectAsState()
    var favorites by remember { mutableIntStateOf(post.favoriteCount) }
    var showCommentInput by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                // 导航到推送详情页，并传递推送 ID
                navController.navigate("postDetail/${post.postId}")
            },
        elevation = CardDefaults.cardElevation(2.dp),
        // elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        // colors = CardDefaults.cardColors(
        //     containerColor = MaterialTheme.colorScheme.primary // 自定义卡片的背景颜色
        // )

    ) {
        Column {
            Image(
                painter = painterResource(id = post.imageUrl),
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = post.title,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 16.sp
            )
            // 截取描述文本以限制字符数量
            val displayDescription = if (post.description.length > 20) {
                post.description.take(20) + "..."
            } else {
                post.description
            }
            Text(
                text = displayDescription,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 14.sp
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 评论按钮和数量
                IconButton(
                    onClick = {
                    showCommentInput = !showCommentInput
                    if (showCommentInput) {
                        // 如果是要显示评论输入，确保软键盘也被显示
                        keyboardController?.show()
                    } else {
                        // 如果是隐藏评论输入，软键盘也应该隐藏
                        keyboardController?.hide()
                    }
                }

                ) {
                    Row {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = "Comment",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${post.commentCount}",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 12.sp
                        )
                    }
                }
                // 在此处处理是否显示评论输入组件的逻辑
                if (showCommentInput) {
                    CommentInputBar(
                        onCommentSubmitted = { comment ->
                        println("提交的评论: $comment")
                        // 根据需要处理评论
                        showCommentInput = false // 可以选择在评论提交后隐藏输入框
                    },
                        onDismissRequest = {
                            // 当用户尝试关闭软键盘（失去焦点）时
                            showCommentInput = false
                        }
                    )
                }


                // 点赞按钮和数量
                IconButton(
                    onClick = {
                    likes++
                    // 可以在这里调用一个函数来更新后端的点赞数
                    // 使用post.postId来标识哪个帖子被点赞，likes为新的点赞数
                    onLikeIncremented(post.postId, likes)
                }
                ) {
                    Row {
                        Icon(
                            Icons.Outlined.ThumbUp,
                            contentDescription = "Like",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${post.likeCount}",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 12.sp
                        )
                    }
                }

                // 收藏按钮和数量
                IconButton(onClick = {
                    favorites++
                    // 可以在这里调用一个函数来更新后端的点赞数
                    // 使用post.postId来标识哪个帖子被点赞，likes为新的点赞数
                    onLikeIncremented(post.postId, favorites)
                }
                    ) {
                    Row {
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${post.favoriteCount}",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 12.sp
                        )
                    }
                }

                // 转发按钮和数量
                IconButton(
                    onClick = { /* 处理点击 */ },
                ) {
                    Row {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${post.shareCount}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PostList(posts: List<Post>, navController: NavController, onLikeIncremented: (String, Int) -> Unit, onDismissRequest: () -> Unit) {
    // 底部导航栏的常见高度为 56.dp，
    // 但可以根据实际的设计调整这个值。
    // 添加额外的 8.dp 是为了提供一些视觉上的间隙。
    val paddingBottom = 56.dp + 36.dp


    LazyColumn(
        // 添加底部内边距
        contentPadding = PaddingValues(bottom = paddingBottom),
        // 其他可能的参数，比如 contentPadding 的 top, start, end
    ) {
        items(posts) { post ->
            // 传入PostItem，并提供onLikeIncremented回调的实现
            PostItem(
                post = post,
                onLikeIncremented = { postId, newLikes ->
                // 在这里实现点赞逻辑，比如更新后端数据库
                onLikeIncremented(postId, newLikes)
            },
                navController,
                onDismissRequest = onDismissRequest
            )
        }
    }
}