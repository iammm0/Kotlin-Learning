package com.example.physicistscard.android.ui.screens.storeScreens.basicItems

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.screens.communityScreens.onLikeIncremented


@Composable
fun ScrollableTapRow(products: List<Product>, navController: NavController) {
    val categories = listOf("物理学家", "理论学说", "经典实验", "物理现象", "尖端技术")
    val (selectedCategory, setSelectedCategory) = remember { mutableStateOf(categories.first()) }

    ScrollableTabRow(
        selectedTabIndex = categories.indexOf(selectedCategory),
        edgePadding = 16.dp,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxWidth()
    ) {
        categories.forEachIndexed { _, category ->
            Tab(
                selected = selectedCategory == category,
                onClick = { setSelectedCategory(category) },
                text = {
                    Text(
                        category,
                        style = TextStyle(
                            fontSize = 14.sp, // 设置字体大小
                            color = MaterialTheme.colorScheme.secondary // 设置字体颜色
                        )
                    )
                }
            )
        }
    }
    // 商品栏目
    // 商品栏目，根据选中的分类筛选商品
    val paddingBottom = 56.dp + 36.dp
    LazyColumn(
        // 添加底部内边距
        contentPadding = PaddingValues(bottom = paddingBottom),
        // 其他可能的参数，比如 contentPadding 的 top, start, end
    ) {
        val filteredProducts = products.filter { it.category == selectedCategory }
        items(filteredProducts) { product ->
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

