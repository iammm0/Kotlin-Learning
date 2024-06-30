package com.example.physicistscard.android.ui.components.layouts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreTopAppBar(text: String,
                   navController: NavController, // 添加NavController参数
                   onAddBagClick: () -> Unit = { navController.navigate("store-card-bag") },
                   onAccountClick: () -> Unit = { navController.navigate("store-management-detail") }
) {

    var searchText by remember { mutableStateOf("") }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = text )
                Spacer(Modifier.width(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary, // 获得焦点时的边框颜色
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary // 未获得焦点时的边框颜色
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            // 处理搜索逻辑
                            // 例如：使用 searchText 进行搜索
                        }),
                        trailingIcon = {
                            IconButton(onClick = {
                                // 当用户点击搜索图标时，执行搜索操作
                                // 这里可以放置触发搜索的逻辑
                            }) {
                                Icon(Icons.Filled.Search, contentDescription = "搜索")
                            }
                        },
                        modifier = Modifier
                            .width(188.dp)
                            .height(30.dp)
                    )
                    IconButton(onClick = onAccountClick) {
                        Icon(Icons.Outlined.Person, contentDescription = "Account")
                    }
                    IconButton(onClick = onAddBagClick) {
                        Icon(Icons.Outlined.Shop, contentDescription = "AddBag")
                    }
                    Spacer(Modifier.width(10.dp))
                }
            }
        },
        actions = {

        },
        modifier = Modifier.height(75.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background, // TopAppBar背景色
            titleContentColor = MaterialTheme.colorScheme.secondary, // 标题文字颜色
            actionIconContentColor = MaterialTheme.colorScheme.secondary // 动作图标颜色
        )
    )
}