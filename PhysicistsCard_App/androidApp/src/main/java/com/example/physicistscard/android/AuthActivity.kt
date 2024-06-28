package com.example.physicistscard.android

import AuthApiServiceImpl
import AuthRepository
import AuthViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.physicistscard.AuthApiService
import com.example.physicistscard.android.ui.components.SystemUiController
import com.example.physicistscard.android.ui.components.navigation.BottomNavItem
import com.example.physicistscard.android.ui.screens.authScreens.LoginEmailScreen
import com.example.physicistscard.android.ui.screens.authScreens.LoginNormalScreen
import com.example.physicistscard.android.ui.screens.authScreens.LoginPhoneScreen
import com.example.physicistscard.android.ui.screens.authScreens.RegisterEmailScreen
import com.example.physicistscard.android.ui.screens.authScreens.RegisterPhoneScreen
import com.example.physicistscard.android.ui.screens.authScreens.StartScreen
import com.example.physicistscard.android.ui.screens.storeScreens.StoreScreen
import com.example.physicistscard.android.ui.themes.MyApplicationTheme
import createCommonHttpClient

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化 HttpClient 和 AuthApiServiceImpl
        val client = createCommonHttpClient()
        val authService = AuthApiServiceImpl(client)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SystemUiController() // 调用来隐藏状态栏
                    AuthNavigation(authService)
                }
            }
        }
    }
}

@Composable
fun AuthNavigation(authService: AuthApiService) {
    val navController = rememberNavController()
    val authRepository = AuthRepository(authService)
    val authViewModel = AuthViewModel(authRepository)
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
        composable("email_login") { LoginEmailScreen(navController, authViewModel) }
        composable(BottomNavItem.Store.route) { StoreScreen(navController) }
    }
}

