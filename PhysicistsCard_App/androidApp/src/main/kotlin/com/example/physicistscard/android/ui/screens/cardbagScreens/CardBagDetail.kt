package com.example.physicistscard.android.ui.screens.cardbagScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.screens.communityScreens.onLikeIncremented
import com.example.physicistscard.android.ui.screens.storeScreens.basicItems.ProductItem

@Composable
fun CardBagDetail(cardBag: CardBag,navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "卡袋ID: ${cardBag.id}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onBackground)
        cardBag.products.forEach { product ->
            ProductItem(
                product,
                onClick = {},
                navController,
                onLikeIncremented = { postId, newLikes ->
                    // 在这里实现点赞逻辑，比如更新后端数据库
                    onLikeIncremented(postId, newLikes)
                }
            )
        }
    }
}
