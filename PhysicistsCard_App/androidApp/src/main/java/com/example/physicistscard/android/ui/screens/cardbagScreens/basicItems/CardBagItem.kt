package com.example.physicistscard.android.ui.screens.cardbagScreens.basicItems

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.physicistscard.android.ui.screens.cardbagScreens.CardBag

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardBagItem(cardBag: CardBag, onSelectionChange: (CardBag) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = {
                    // 处理普通点击事件（如果需要）
                },
                onLongClick = {
                    // 长按时切换选中状态
                    onSelectionChange(cardBag)
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (cardBag.isSelected) Color(0x9EB388FF) else MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Text(
                text = "${cardBag.id}号 购物卡袋", // 根据选中状态显示标记
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.secondary
            )
            cardBag.products.forEach { product ->
                Text(
                    text = product.name,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
