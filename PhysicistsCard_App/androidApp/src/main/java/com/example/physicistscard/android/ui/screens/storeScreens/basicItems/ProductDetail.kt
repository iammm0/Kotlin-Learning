package com.example.physicistscard.android.ui.screens.storeScreens.basicItems

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
import androidx.compose.ui.unit.dp
import com.example.physicistscard.android.ui.screens.storeScreens.getProductById

@Composable
fun ProductDetail(productId: String?) {
    val product = productId?.let { getProductById(it) }

    Spacer(Modifier.height(36.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp), // 根据需要调整外部填充来控制Card的大小和位置
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // 根据需要调整卡片的高度
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary // 自定义卡片的背景颜色
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp), // 内部填充，控制内容与Card边缘的距离
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 假设已经获取了商品对象
            product?.let {
                // 商品图片，确保你的product对象有imageUrl属性
                // Image(
                //     painter = painterResource(id = it.imageUrl),
                //     contentDescription = "${it.name} image",
                //     modifier = Modifier
                //         .fillMaxWidth()
                //         .height(200.dp),
                //     contentScale = ContentScale.Crop
                // )

                Image(
                    painter = painterResource(id = it.imageUrl),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                // 商品名称
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                // 商品价格
                Text(
                    text = " ${it.price} ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // 商品描述
                Text(
                    text = it.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }?: run {
                Text("商品未找到")
            }
        }
    }
}