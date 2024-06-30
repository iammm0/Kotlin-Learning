package com.example.physicistscard.android.ui.screens.storeScreens.basicItems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.screens.communityScreens.basicItems.CommentInputBar

// 假设的商品类型
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val price: String, // 新增价格字段
    val imageUrl: Int,
    val commentCount: Int = 0, // 添加评论数，默认值为0
    val shareCount: Int = 0, // 添加转发数，默认值为0
    val likeCount: Int = 0, // 添加点赞数，默认值为0
    val favoriteCount: Int = 0
)

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    navController: NavController,
    onLikeIncremented: (String, Int) -> Unit
) {

    var likes by remember { mutableIntStateOf(product.likeCount) }
    var favorites by remember { mutableIntStateOf(product.favoriteCount) }
    var showCommentInput by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = Modifier
            .padding(6.dp)
            .clickable {
                // 导航到商品详情页，并传递商品ID
                navController.navigate("productDetail/${product.id}")
            }
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        // elevation = 6.dp,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary // 自定义卡片的背景颜色
        )
        // backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp).background(color = Color.LightGray)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    // 商品名称
                    Text(
                        text = product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    // 截取描述文本以限制字符数量
                    val displayDescription = if (product.description.length > 28) {
                        product.description.take(28) + "..."
                    } else {
                        product.description
                    }
                    // 商品描述
                    Text(
                        text = displayDescription,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 评论按钮和数量
                IconButton(onClick = {
                    showCommentInput = !showCommentInput
                    if (showCommentInput) {
                        // 如果是要显示评论输入，确保软键盘也被显示
                        keyboardController?.show()
                    } else {
                        // 如果是隐藏评论输入，软键盘也应该隐藏
                        keyboardController?.hide()
                    }
                }) {
                    Row {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = "Comment",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${product.commentCount}",
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
                IconButton(onClick = {
                    likes++
                    // 可以在这里调用一个函数来更新后端的点赞数
                    // 使用post.postId来标识哪个帖子被点赞，likes为新的点赞数
                    onLikeIncremented(product.id, likes)
                }) {
                    Row {
                        Icon(
                            Icons.Outlined.ThumbUp,
                            contentDescription = "Like",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${product.likeCount}",
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
                    onLikeIncremented(product.id, favorites)
                }) {
                    Row {
                        Icon(
                            Icons.Rounded.Star,
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${product.favoriteCount}",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 12.sp
                        )
                    }
                }

                // 转发按钮和数量
                IconButton(onClick = { /* 处理点击 */ }) {
                    Row {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "${product.shareCount}",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
