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
import com.example.physicistscard.android.ui.components.inputs.RowWithTextFieldAndButton

@Composable
fun RegisterPhoneScreen(navController: NavController) {
    // 实现使用手机号注册的界面
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phoneCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(148.dp))
        LoginAndRegisterOutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            labelText = "手机号码",
            isPassword = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        LoginAndRegisterOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            labelText = "密码",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        LoginAndRegisterOutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            labelText = "确认密码",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        RowWithTextFieldAndButton(
            textFieldValue = phoneCode,
            onTextFieldValueChange = { phoneCode = it },
            onButtonClick = { /*TODO*/ },
            textFieldLabel = "验证码",
            buttonText = "发送验证码"
        )
        Spacer(modifier = Modifier.height(32.dp))
        LoginAndRegisterButton(text = "完成注册") {
            // navController.navigate("")
        }
    }
}