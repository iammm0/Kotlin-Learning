package com.example.physicistscard.android.ui.screens.storeScreens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.screens.communityScreens.basicItems.Comment
import com.example.physicistscard.android.ui.screens.storeScreens.basicItems.Product
import com.example.physicistscard.android.ui.screens.storeScreens.basicItems.ProductSection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productId: String?, comments: List<Comment>, navController: NavController) {
    // Column {
    //     ProductDetail(productId = productId)
    // }
    ProductSection(comments = comments, commentTitle = "商品评论", productId = productId)
}

fun getProductById(productId: String): Product? {
    // 在商品列表中查找匹配的商品ID并返回对应的商品
    return physicistsProducts.find { it.id == productId }!!
}

