package com.example.physicistscard.android.ui.components.navigation

import ChatUI
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.physicistscard.android.ui.screens.accountScreen.AddAccountScreen
import com.example.physicistscard.android.ui.screens.accountScreen.BindEmailScreen
import com.example.physicistscard.android.ui.screens.accountScreen.BindPhoneScreen
import com.example.physicistscard.android.ui.screens.accountScreen.ResetPasswordScreen
import com.example.physicistscard.android.ui.screens.accountScreen.SwitchAvatarScreen
import com.example.physicistscard.android.ui.screens.accountScreen.UnsubscribeAccountScreen
import com.example.physicistscard.android.ui.screens.cardbagScreens.CardBagScreen
import com.example.physicistscard.android.ui.screens.chatScreens.FriendProfileScreen
import com.example.physicistscard.android.ui.screens.chatScreens.UserFriendsScreen
import com.example.physicistscard.android.ui.screens.chatScreens.UserInteractionsScreen
import com.example.physicistscard.android.ui.screens.communityScreens.CommunityScreen
import com.example.physicistscard.android.ui.screens.communityScreens.PostDetailScreen
import com.example.physicistscard.android.ui.screens.communityScreens.commentsSample
import com.example.physicistscard.android.ui.screens.communityScreens.communityPosts
import com.example.physicistscard.android.ui.screens.deliveryScreen.DeliveryDetailsScreen
import com.example.physicistscard.android.ui.screens.deliveryScreen.sampleLogisticsInfo
import com.example.physicistscard.android.ui.screens.editScreens.EditPostScreen
import com.example.physicistscard.android.ui.screens.managementScreen.ManagementScreen
import com.example.physicistscard.android.ui.screens.managementScreen.accountManagement.AccountManagementsScreen
import com.example.physicistscard.android.ui.screens.managementScreen.communityManagement.CommunityManagementsScreen
import com.example.physicistscard.android.ui.screens.managementScreen.communityManagement.UserCollectionsScreen
import com.example.physicistscard.android.ui.screens.managementScreen.communityManagement.UserPostsScreen
import com.example.physicistscard.android.ui.screens.managementScreen.storeManagement.StoreManagementsScreen
import com.example.physicistscard.android.ui.screens.orderScreen.OrderDetail
import com.example.physicistscard.android.ui.screens.orderScreen.OrderScreen
import com.example.physicistscard.android.ui.screens.orderScreen.sampleOrders
import com.example.physicistscard.android.ui.screens.settingsScreens.AboutPhysCardScreen
import com.example.physicistscard.android.ui.screens.settingsScreens.FeedbackScreen
import com.example.physicistscard.android.ui.screens.settingsScreens.LanguageSettingsScreen
import com.example.physicistscard.android.ui.screens.settingsScreens.SettingsScreen
import com.example.physicistscard.android.ui.screens.settingsScreens.ThemeSettingsScreen
import com.example.physicistscard.android.ui.screens.storeScreens.ProductDetailScreen
import com.example.physicistscard.android.ui.screens.storeScreens.StoreScreen
import com.example.physicistscard.transmissionModels.auth.user.Role
import com.example.physicistscard.transmissionModels.auth.user.User
import kotlinx.datetime.toLocalDateTime

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomNavItem.Store.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
        }
        ) {
        composable(BottomNavItem.Store.route) { StoreScreen(navController) }
        composable(BottomNavItem.Community.route) { CommunityScreen(navController) }
        composable(BottomNavItem.Management.route) { ManagementScreen(navController) }
        composable(BottomNavItem.Settings.route) { SettingsScreen(navController) }
        composable("productDetail/{productId}") { backStackEntry ->
            // 从路由参数中获取商品ID
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailScreen(productId = productId, comments = commentsSample, navController)
        }
        composable("store-management-detail") { StoreManagementsScreen(navController) }
        composable("community-management-detail") { CommunityManagementsScreen(navController) }
        composable("account-management-detail") { AccountManagementsScreen(navController) }
        composable("postDetail/{postId}") { backStackEntry ->
            // 从路由参数中获取推送ID
            val postId = backStackEntry.arguments?.getString("postId")
            PostDetailScreen(postId = postId, comments = commentsSample, navController)
        }
        composable("store-card-bag") { CardBagScreen(navController) }
        composable("store-order-detail") { OrderScreen(sampleOrders, navController) }
        composable("store-delivery-status") { DeliveryDetailsScreen(sampleLogisticsInfo, navController) }
        composable("orderDetail/{orderDetail}") {backStackEntry ->
            // 从路由参数中获取订单ID
            val orderId = backStackEntry.arguments?.getString("orderId")
            OrderDetail(orderId, navController)
        }
        composable("community-edit-post") { EditPostScreen(navController) }
        composable("community-my-post") { UserPostsScreen(communityPosts = communityPosts, navController) }
        composable("community-my-favorite") { UserCollectionsScreen(communityPosts = communityPosts, navController) }
        composable("community-interactive-message") { UserInteractionsScreen(navController) }
        composable("user_interactions") {
            UserInteractionsScreen(navController = navController)
        }
        // 传递用户列表到 user_friends 页面
        composable("user_friends") {
            val userFriends = getUserFriends() // 从数据源或API获取用户列表
            UserFriendsScreen(navController = navController, friends = userFriends)
        }

        // 传递用户列表到 community-my-friends 页面
        composable("community-my-friends") {
            val communityFriends = getCommunityFriends() // 从数据源或API获取用户列表
            UserFriendsScreen(navController = navController, friends = communityFriends)
        }
        composable("chat/{friendId}") { backStackEntry ->
            val friendId = backStackEntry.arguments?.getString("friendId")
            ChatUI(
                onSend = { /* Handle sending message to backend */ },
                navController = navController,
                currentUser = getCurrentUser(), // 假设有一个获取当前用户的方法
                friendId = friendId
            )
        }
        composable("friend_profile/{friendId}") { backStackEntry ->
            FriendProfileScreen(navController = navController, friendId = backStackEntry.arguments?.getString("friendId"))
        }
        composable("account_management") {
            AccountManagementsScreen(navController = navController)
        }
        composable("account-switch-avatar") {
            SwitchAvatarScreen(navController = navController)
        }
        composable("account-bind-phone") {
            BindPhoneScreen(navController = navController)
        }
        composable("account-bind-email") {
            BindEmailScreen(navController = navController)
        }
        composable("account-reset-password") {
            ResetPasswordScreen(navController = navController)
        }
        composable("account-unsubscribe") {
            UnsubscribeAccountScreen(navController = navController)
        }
        composable("account-switch") {
            AddAccountScreen(navController = navController)
        }
        composable("setting-theme") {
            ThemeSettingsScreen(navController = navController)
        }
        composable("setting-language") {
            LanguageSettingsScreen(navController = navController)
        }
        composable("setting-feedback") {
            FeedbackScreen(navController = navController)
        }
        composable("setting-appmore") {
            AboutPhysCardScreen(navController = navController)
        }
    }
}

// 获取当前用户的模拟方法
fun getCurrentUser(): User {
    return User(
        userId = "0",
        username = "current_user",
        displayName = "Current User",
        email = "current@example.com",
        phone = "9876543210",
        passwordHash = "hashed_password",
        avatarUrl = null,
        bio = "This is my bio",
        isOnline = true,
        lastActive = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
        registerDate = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
        isEmailVerified = true,
        isPhoneVerified = true,
        role = Role.USER
    )
}

// 模拟从数据源或API获取用户列表的方法
fun getUserFriends(): List<User> {
    return listOf(
        User(
            userId = "1",
            username = "friend_1",
            displayName = "Friend 1",
            email = "friend1@example.com",
            phone = "1234567890",
            passwordHash = "hashed_password",
            avatarUrl = null,
            bio = "This is Friend 1's bio",
            isOnline = true,
            lastActive = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
            registerDate = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
            isEmailVerified = true,
            isPhoneVerified = true,
            role = Role.USER
        ),
        User(
            userId = "2",
            username = "friend_2",
            displayName = "Friend 2",
            email = "friend2@example.com",
            phone = "0987654321",
            passwordHash = "hashed_password",
            avatarUrl = null,
            bio = "This is Friend 2's bio",
            isOnline = false,
            lastActive = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
            registerDate = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
            isEmailVerified = true,
            isPhoneVerified = true,
            role = Role.USER
        )
    )
}

// 另一个模拟方法获取社区好友
fun getCommunityFriends(): List<User> {
    return listOf(
        User(
            userId = "3",
            username = "community_friend_1",
            displayName = "Community Friend 1",
            email = "communityfriend1@example.com",
            phone = "1112223333",
            passwordHash = "hashed_password",
            avatarUrl = null,
            bio = "This is Community Friend 1's bio",
            isOnline = true,
            lastActive = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
            registerDate = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
            isEmailVerified = true,
            isPhoneVerified = true,
            role = Role.USER
        ),
        User(
            userId = "4",
            username = "community_friend_2",
            displayName = "Community Friend 2",
            email = "communityfriend2@example.com",
            phone = "4445556666",
            passwordHash = "hashed_password",
            avatarUrl = null,
            bio = "This is Community Friend 2's bio",
            isOnline = false,
            lastActive = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
            registerDate = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
            isEmailVerified = true,
            isPhoneVerified = true,
            role = Role.USER
        )
    )
}
