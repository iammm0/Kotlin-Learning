package com.example.physicistscard.android.ui.screens.authScreens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.buttons.LoginAndRegisterButton
import com.example.physicistscard.android.ui.components.icons.AppLogo
import com.example.physicistscard.android.ui.components.inputs.LoginAndRegisterOutlinedTextField

@Composable
fun LoginNormalScreen(navController: NavController) {

    var identitifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(160.dp))
        LoginAndRegisterOutlinedTextField(
            value = identitifier,
            onValueChange = { identitifier = it },
            labelText = "邮箱地址/手机号码",
            isPassword = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        LoginAndRegisterOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            labelText = "密码",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoginAndRegisterButton(text = "登录") {
            // navController.navigate("main")
        }
        Spacer(modifier = Modifier.height(20.dp))
        LoginAndRegisterButton(text = "邮箱验证登录") {
            navController.navigate("email_login")
        }
        Spacer(modifier = Modifier.height(20.dp))
        LoginAndRegisterButton(text = "手机号码验证登录") {
            navController.navigate("phone_login")
        }
    }
}
