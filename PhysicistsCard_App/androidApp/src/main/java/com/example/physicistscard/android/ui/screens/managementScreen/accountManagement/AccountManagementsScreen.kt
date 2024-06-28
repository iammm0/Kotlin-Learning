package com.example.physicistscard.android.ui.screens.managementScreen.accountManagement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar
import com.example.physicistscard.android.ui.screens.managementScreen.basicItems.ManagementDetailItem
import com.example.physicistscard.android.ui.screens.managementScreen.basicItems.ManagementDetailList

@Composable
fun AccountManagementsScreen(navController: NavController) {
    val managementDetailItems = listOf<ManagementDetailItem>(
        ManagementDetailItem("切换头像", Icons.Outlined.AccountCircle,"仔细哟～斟酌哟～这可是咱的脸面", "account-switch-avatar"),
        ManagementDetailItem("绑定手机", Icons.Outlined.Call, "邮箱国来的点这个联姻", "account-bind-phone"),
        ManagementDetailItem("绑定邮箱", Icons.Outlined.Email, "电话国来的点这个联姻", "account-bind-email"),
        ManagementDetailItem("重置密码", Icons.Outlined.Build,"终于觉得自己当初所下的决定不中用了吧", "account-reset-password"),
        ManagementDetailItem("注销账户", Icons.Outlined.Lock, "在 PhysCard 的世界中消失一会", "account-unsubscribe"),
        ManagementDetailItem("添加账户", Icons.Outlined.Add, "换个身份？——>满血复活！", "account-switch"),
    )

    val paddingBottom = 56.dp + 8.dp
    LazyColumn(
        // 添加底部内边距
        contentPadding = PaddingValues(bottom = paddingBottom),
        // 其他可能的参数，比如 contentPadding 的 top, start, end
    ) {
        item {
            Column {
                TopAppBar(text = "账户管理")
                ManagementDetailList(managementDetailItems, navController)
            }
        }
    }

}