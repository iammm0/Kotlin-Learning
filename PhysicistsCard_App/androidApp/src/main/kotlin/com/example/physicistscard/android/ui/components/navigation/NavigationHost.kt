package com.example.physicistscard.android.ui.components.navigation

import AuthViewModel
import ChatUI
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.physicistscard.android.auth.LoginEmailScreen
import com.example.physicistscard.android.auth.LoginNormalScreen
import com.example.physicistscard.android.auth.LoginPhoneScreen
import com.example.physicistscard.android.auth.RegisterEmailScreen
import com.example.physicistscard.android.auth.RegisterPhoneScreen
import com.example.physicistscard.android.auth.StartScreen
import com.example.physicistscard.android.community.chat.FriendProfileScreen
import com.example.physicistscard.android.community.chat.UserFriendsScreen
import com.example.physicistscard.android.community.chat.UserInteractionsScreen
import com.example.physicistscard.android.community.main.CommunityScreen
import com.example.physicistscard.android.community.main.PostDetailScreen
import com.example.physicistscard.android.community.main.commentsSample
import com.example.physicistscard.android.manager.accountManagement.AccountManagementsScreen
import com.example.physicistscard.android.manager.communityManagement.CommunityManagementsScreen
import com.example.physicistscard.android.manager.communityManagement.UserCollectionsScreen
import com.example.physicistscard.android.manager.communityManagement.UserPostsScreen
import com.example.physicistscard.android.ui.screens.settingsScreens.AboutPhysCardScreen
import com.example.physicistscard.android.ui.screens.settingsScreens.FeedbackScreen
import com.example.physicistscard.android.ui.screens.settingsScreens.LanguageSettingsScreen
import com.example.physicistscard.android.ui.screens.settingsScreens.SettingsScreen
import com.example.physicistscard.android.ui.screens.settingsScreens.ThemeSettingsScreen

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "start",
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
        // 认证相关界面
        composable("start") { StartScreen(navController) }
        composable("login") { LoginNormalScreen(navController) }
        composable("phone_register") { RegisterPhoneScreen(navController) }
        composable("email_register") { RegisterEmailScreen(navController) }
        composable("phone_login") { LoginPhoneScreen(navController) }
        composable("email_login") { LoginEmailScreen(navController) }

        // 社区相关界面
        composable(BottomNavItem.Community.route) {
            CommunityScreen(navController)
        }
        composable("postDetail/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            PostDetailScreen(postId = postId, comments = commentsSample, navController)
        }
        composable("community-management-detail") {
            CommunityManagementsScreen(navController)
        }

        // 用户管理界面
        composable("account-management-detail") {
            AccountManagementsScreen(navController)
        }
        composable("community-my-post") {
            UserPostsScreen(navController)
        }
        composable("community-my-favorite") {
            UserCollectionsScreen(navController)
        }
        composable("user_friends") {
            UserFriendsScreen(navController)
        }
        composable("user_interactions") {
            UserInteractionsScreen(navController)
        }
        composable("friend_profile/{friendId}") { backStackEntry ->
            FriendProfileScreen(navController = navController, friendId = backStackEntry.arguments?.getString("friendId"))
        }
        composable("chat/{friendId}") {
            ChatUI(navController)
        }

        // 设置相关界面
        composable(BottomNavItem.Settings.route) {
            SettingsScreen(navController)
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

        // 注释掉与商城相关的界面
        /*
        composable(BottomNavItem.Store.route) {
            StoreScreen(navController)
        }
        composable("productDetail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailScreen(productId = productId, comments = commentsSample, navController)
        }
        composable("store-management-detail") {
            StoreManagementsScreen(navController)
        }
        composable("store-card-bag") {
            CardBagScreen(navController)
        }
        composable("store-order-detail") {
            OrderScreen(sampleOrders, navController)
        }
        composable("store-delivery-status") {
            DeliveryDetailsScreen(sampleLogisticsInfo, navController)
        }
        composable("orderDetail/{orderDetail}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")
            OrderDetail(orderId, navController)
        }
        */
    }
}
