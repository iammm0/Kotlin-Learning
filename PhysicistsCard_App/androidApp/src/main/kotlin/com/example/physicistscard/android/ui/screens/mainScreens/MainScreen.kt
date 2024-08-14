package com.example.physicistscard.android.ui.screens.mainScreens

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.physicistscard.android.ui.components.navigation.BottomNavItem
import com.example.physicistscard.android.ui.components.navigation.NavigationHost
import com.example.physicistscard.android.viewModels.FriendshipViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    friendshipViewModel: FriendshipViewModel
) {

    val items = listOf(
        BottomNavItem.Store,
        BottomNavItem.Community,
        BottomNavItem.Management,
        BottomNavItem.Settings
    )

    // 监听当前导航栈的变化
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 定义需要隐藏底部导航栏的路由集合
    val routesToHideBottomBar = setOf("postDetail/{postId}",
        "productDetail/{productId}",
        "community-edit-post",
        "community-my-post",
        "community-my-favorite",
        "orderDetail/{orderDetail}",
        "community-management-detail",
        "store-order-detail",
        "account-management-detail",
        "store-delivery-status",
        "chat/{friendId}"
    )

    // 检查当前路由是否在集合中，以决定是否显示底部导航栏
    val showBottomBar = currentRoute?.let { route ->
        // 检查当前路由是否匹配集合中的任一路由模式
        routesToHideBottomBar.none { it in route }
    } ?: true

    Scaffold(
        bottomBar = {
            // 根据showBottomBar的值来决定是否展示底部导航栏
            if (showBottomBar) {
                // BottomNavigationBar(navController)
                // Main2Frame(navController)
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background,
                    // modifier = Modifier.height(72.dp)
                ) {
                    val currentRoute = navController.currentDestination?.route
                    items.forEachIndexed { _, bottomNavItem->
                        NavigationBarItem(
                            selected = currentRoute == bottomNavItem.route,
                            onClick = {
                                navController.navigate(bottomNavItem.route) {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) { saveState = true }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { // 图标
                                Icon(
                                    imageVector = bottomNavItem.icon,
                                    contentDescription = null
                                )
                            },
                            label = { // 文字
                                Text(
                                    text = bottomNavItem.title,
                                    style = TextStyle(
                                        fontSize = 12.sp, // 设置字体大小
                                    )
                                )
                            },
                            alwaysShowLabel = false, colors = NavigationBarItemDefaults.colors( // 颜色配置
                                selectedIconColor = Color(0x9EB388FF),
                                selectedTextColor = Color(0x9EB388FF),
                                unselectedIconColor = Color(0xff999999),
                                unselectedTextColor = Color(0xff999999)
                            )
                        )
                    }
                }
            }
        }
    ) {
        NavigationHost(navController, friendshipViewModel)
    }
}