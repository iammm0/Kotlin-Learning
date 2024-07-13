package com.example.physicistscard.android.ui.components.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.physicistscard.android.ui.screens.authScreens.LoginEmailScreen
import com.example.physicistscard.android.ui.screens.authScreens.LoginNormalScreen
import com.example.physicistscard.android.ui.screens.authScreens.LoginPhoneScreen
import com.example.physicistscard.android.ui.screens.authScreens.RegisterEmailScreen
import com.example.physicistscard.android.ui.screens.authScreens.RegisterPhoneScreen
import com.example.physicistscard.android.ui.screens.authScreens.StartScreen
import com.example.physicistscard.android.ui.screens.storeScreens.StoreScreen
import com.example.physicistscard.businessLogic.IAuthService

@Composable
fun AuthNavigation(authService: IAuthService) {
    val navController = rememberNavController()
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
        composable("start") { StartScreen(navController) }
        composable("login") { LoginNormalScreen(navController) }
        composable("phone_register") { RegisterPhoneScreen(navController) }
        composable("email_register") { RegisterEmailScreen(navController) }
        composable("phone_login") { LoginPhoneScreen(navController) }
        composable("email_login") { LoginEmailScreen(navController, authService) }
        composable(BottomNavItem.Store.route) { StoreScreen(navController) }
    }
}
