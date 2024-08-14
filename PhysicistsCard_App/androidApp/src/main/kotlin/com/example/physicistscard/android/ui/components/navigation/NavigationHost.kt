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
import com.example.physicistscard.android.viewModels.FriendshipViewModel
import com.example.physicistscard.transmissionModels.auth.user.Role
import com.example.physicistscard.transmissionModels.auth.user.User
import kotlinx.datetime.toLocalDateTime

@Composable
fun NavigationHost(
    navController: NavHostController,
    friendshipViewModel: FriendshipViewModel
) {
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
        // 好友界面和聊天界面导航
        composable("user_interactions") {
            UserInteractionsScreen(navController = navController)
        }
        composable("user_friends") {
            // 使用视图模型加载好友列表
            friendshipViewModel.loadFriends()
            UserFriendsScreen(navController = navController, friendshipViewModel = friendshipViewModel)
        }

        // 聊天界面
        composable("chat/{friendId}") { backStackEntry ->
            val friendId = backStackEntry.arguments?.getString("friendId")?: ""
            ChatUI(
                navController = navController,
                currentUser = getCurrentUser(),
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
        email = "current@example.com",
        phone = "9876543210",
        passwordHash = "hashed_password",
        avatarUrl = null,
        bio = "This is my bio",
        registerDate = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
        isEmailVerified = true,
        isPhoneVerified = true,
        role = Role.USER
    )
}

