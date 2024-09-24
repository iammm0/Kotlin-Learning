package com.example.physicistscard.android.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.inputs.LoginAndRegisterOutlinedTextField
import com.example.physicistscard.android.ui.components.buttons.LoginAndRegisterButton
import com.example.physicistscard.android.ui.components.icons.AppLogo
import com.example.physicistscard.android.ui.components.layouts.TopAppBar

@Composable
fun ResetPasswordScreen(navController: NavController) {
    val oldPassword = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            text = "重置密码",
        )
        Spacer(modifier = Modifier.height(30.dp))
        AppLogo()
        Spacer(modifier = Modifier.height(30.dp))
        LoginAndRegisterOutlinedTextField(
            value = oldPassword.value,
            onValueChange = { oldPassword.value = it },
            labelText = "输入旧密码",
            isPassword = true,
            modifier = Modifier.width(330.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        LoginAndRegisterOutlinedTextField(
            value = newPassword.value,
            onValueChange = { newPassword.value = it },
            labelText = "输入新密码",
            isPassword = true,
            modifier = Modifier.width(330.dp)
        )
        Spacer(modifier = Modifier.height(42.dp))
        LoginAndRegisterButton(
            text = "重置密码",
            onClick = {
                // Handle reset password
            }
        )
    }
}
