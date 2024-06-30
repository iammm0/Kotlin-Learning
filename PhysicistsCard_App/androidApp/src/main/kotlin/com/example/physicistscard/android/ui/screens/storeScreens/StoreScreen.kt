package com.example.physicistscard.android.ui.screens.storeScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.physicistscard.android.R
import com.example.physicistscard.android.ui.components.layouts.StoreTopAppBar
import com.example.physicistscard.android.ui.screens.storeScreens.basicItems.Product
import com.example.physicistscard.android.ui.screens.storeScreens.basicItems.ScrollableTapRow

val physicistsProducts = listOf(
    Product(id = "1", name = "艾萨克·牛顿", category = "物理学家", imageUrl =  R.drawable.newton,description = "经典物理学的奠基人之一，提出了万有引力定律。", price = "¥6"),
    Product(id = "2", name = "阿尔伯特·爱因斯坦", category = "物理学家", imageUrl = R.drawable.albert,description = "相对论的创立者，对现代物理学产生了深远影响。", price = "¥6"),
    Product(id = "4", name = "尼尔斯·玻尔", category = "物理学家", imageUrl = R.drawable.bohr,description = "原子结构和量子力学的先驱。", price = "¥6"),
    Product(id = "3", name = "詹姆斯·克拉克·麦克斯韦", category = "物理学家", imageUrl = R.drawable.maxwell,description = "电磁理论的奠基人，提出了麦克斯韦方程组。", price = "¥6"),
    Product(id = "5", name = "伽利略·伽利雷", category = "物理学家", imageUrl = R.drawable.galileo, description = "自然科学的诞生要归功于伽利略。。", price = "¥6"),
    )

    // ... 省略中间的条目以节省空间 ...

@Composable
fun StoreScreen(navController: NavController) {

    val searchTextState = remember { mutableStateOf(TextFieldValue("")) }

    Column {
        StoreTopAppBar(
            text = "商城",
            navController = navController
        )
        ScrollableTapRow(physicistsProducts, navController)
    }
}


