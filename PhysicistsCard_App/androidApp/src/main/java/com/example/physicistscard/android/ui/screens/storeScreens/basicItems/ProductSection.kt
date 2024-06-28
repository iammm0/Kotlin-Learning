package com.example.physicistscard.android.ui.screens.storeScreens.basicItems

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.physicistscard.android.ui.screens.communityScreens.basicItems.Comment
import com.example.physicistscard.android.ui.screens.communityScreens.basicItems.CommentCard

@Composable
fun ProductSection(comments: List<Comment>, commentTitle: String, productId: String?) {
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        item {
            ProductDetail(productId = productId)
        }
        item {
            Text(
                text = commentTitle,
                style = TextStyle(
                    fontSize = 24.sp, // 设置字体大小
                    color = MaterialTheme.colorScheme.secondary // 设置字体颜色
                ),
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 18.dp),
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Spacer(Modifier.height(8.dp))
        }
        items(comments) { comment ->
            CommentCard(comment = comment)
        }
    }
}