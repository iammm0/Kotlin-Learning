package com.example.physicistscard.android.ui.screens.cardbagScreens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.screens.cardbagScreens.basicItems.CardBagItem
import com.example.physicistscard.android.ui.screens.storeScreens.basicItems.Product


data class CardBag(
    val id: Int,
    var isSelected: Boolean = false, // 默认所有卡袋都未被选中
    val products: MutableList<Product> = mutableListOf() // 添加产品列表
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardBagScreen(navController: NavController) {
    val cardBags = remember { mutableStateListOf<CardBag>() }
    val counter = remember { mutableIntStateOf(0) }
    val isAllSelected = remember { mutableStateOf(false) } // 添加全选状态

    fun handleSelectionChange(updatedBag: CardBag, action: String) {
        when (action) {
            "add" -> {
                cardBags.add(updatedBag)
                counter.intValue += 1
            }
            "delete" -> {
                cardBags.removeAll { it.isSelected }
            }
            "update" -> {
                val index = cardBags.indexOfFirst { it.id == updatedBag.id }
                if (index != -1) {
                    cardBags[index] = updatedBag
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("购物卡袋") },
                actions = {
                    IconButton(onClick = {
                        cardBags.add(CardBag(id = counter.intValue))
                        counter.intValue += 1
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add One")
                    }
                    IconButton(onClick = {
                        cardBags.removeAll { it.isSelected }
                        isAllSelected.value = cardBags.isNotEmpty() && cardBags.all { it.isSelected }
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Selected")
                    }
                    IconButton(
                        onClick = {
                            isAllSelected.value = !isAllSelected.value
                            cardBags.forEachIndexed { index, cardBag ->
                                cardBags[index] = cardBag.copy(isSelected = isAllSelected.value)
                            } },
                        enabled = cardBags.isNotEmpty() // 当没有卡袋时，全选按钮不可用
                        ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Select All",
                            tint = if (isAllSelected.value) Color(0x9EB388FF) else MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background, // TopAppBar背景色
                    titleContentColor = MaterialTheme.colorScheme.secondary, // 标题文字颜色
                    actionIconContentColor = MaterialTheme.colorScheme.secondary // 动作图标颜色
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            cardBags.forEach { cardBag ->
                CardBagItem(
                    cardBag = cardBag,
                    onSelectionChange = { selectedBag ->
                        handleSelectionChange(selectedBag.copy(isSelected = !selectedBag.isSelected), "update")
                    },
                    navController = navController
                )
            }
        }
    }
}

