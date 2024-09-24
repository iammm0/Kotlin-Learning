package com.example.physicistscard.android.manager.communityManagement

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar
import com.example.physicistscard.android.ui.screens.communityScreens.basicItems.Post

@Composable
fun UserCollectionsScreen(communityPosts: List<Post>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        item {
            TopAppBar(text = "我的收藏")
        }

        items(communityPosts) { post ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                        // 导航到推送详情页，并传递推送 ID
                        navController.navigate("postDetail/${post.postId}")
                    },
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary // 自定义卡片的背景颜色
                )
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
                }
            }
        }
    }
}