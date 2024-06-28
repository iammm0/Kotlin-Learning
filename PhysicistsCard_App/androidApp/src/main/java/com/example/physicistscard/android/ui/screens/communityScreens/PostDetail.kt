package com.example.physicistscard.android.ui.screens.communityScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.physicistscard.android.ui.screens.communityScreens.basicItems.Post

@Composable
fun PostDetail(postId: String?) {
    // 假设getPostById是一个能够根据postId获取到Post对象的函数
    val post = postId?.let { getPostById(it) }

    Spacer(Modifier.height(36.dp))

    // 使用Card组件包裹内容
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp), // 根据需要调整外部填充来控制Card的大小和位置
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // 根据需要调整卡片的高度
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary // 自定义卡片的背景颜色
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp), // 内部填充，控制内容与Card边缘的距离
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 保持PostDetail的原有布局和逻辑
            if (post != null) {
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = painterResource(id = post.imageUrl),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = post.description,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 14.sp
                )
            }
        }
    }
}

fun getPostById(postId: String): Post? {
    // 在商品列表中查找匹配的商品ID并返回对应的商品
    return communityPosts.find { it.postId == postId }!!
}