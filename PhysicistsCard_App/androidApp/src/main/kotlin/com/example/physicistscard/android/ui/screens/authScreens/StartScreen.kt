package com.example.physicistscard.android.ui.screens.authScreens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.activities.MainActivity
import com.example.physicistscard.android.ui.components.buttons.LoginAndRegisterButton
import com.example.physicistscard.android.ui.components.icons.AppLogo
import com.example.physicistscard.android.ui.components.navigation.BottomNavItem

@Composable
fun StartScreen(navController: NavController) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(188.dp))
        LoginAndRegisterButton(text = "登录") {

            navController.navigate("login")
        }
        Spacer(modifier = Modifier.height(26.dp))
        LoginAndRegisterButton(text = "使用邮箱注册") {
            navController.navigate("email_register")
        }
        Spacer(modifier = Modifier.height(26.dp))
        LoginAndRegisterButton(text = "使用手机号码注册") {
            navController.navigate("phone_register")
        }
        Spacer(modifier = Modifier.height(26.dp))
        LoginAndRegisterButton(text = "进入PHYSICISTS CARD") {
            navController.navigate(BottomNavItem.Store.route)

            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("EXTRA_USER_ID", "userId") // 假设您有一个userId要传递
            }
            context.startActivity(intent)
            if (context is Activity) {
                context.finish()
            }
        }
    }
}