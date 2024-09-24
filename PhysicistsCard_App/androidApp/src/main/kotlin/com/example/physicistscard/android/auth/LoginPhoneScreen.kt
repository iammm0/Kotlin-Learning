package com.example.physicistscard.android.auth

import AuthViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.buttons.LoginAndRegisterButton
import com.example.physicistscard.android.ui.components.icons.AppLogo
import com.example.physicistscard.android.ui.components.inputs.LoginAndRegisterOutlinedTextField

@Composable
fun LoginPhoneScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    var phoneNumber by remember { mutableStateOf("") }
    var phoneCode by remember { mutableStateOf("") }

    val loginState by authViewModel.loginWithPhoneCodeState.collectAsState()

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
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            labelText = "手机号码",
            isPassword = false,
            modifier = Modifier.width(330.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LoginAndRegisterOutlinedTextField(
            value = phoneCode,
            onValueChange = { phoneCode = it },
            labelText = "验证码",
            isPassword = false,
            modifier = Modifier.width(330.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LoginAndRegisterButton(
            text = "发送验证码",
            onClick = {
                authViewModel.sendVerificationCode(phoneNumber, "phone_login")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LoginAndRegisterButton(
            text = "登录",
            onClick = {
                authViewModel.loginWithPhoneCode(phoneNumber, phoneCode)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        loginState?.let { result ->
            when {
                result.isSuccess -> {
                    Text("登录成功")
                    navController.navigate("main_screen") // 替换为实际的主界面路径
                }
                result.isFailure -> {
                    val exception = result.exceptionOrNull()
                    Text(
                        text = "登录失败: ${exception?.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        if (loginState == null) {
            CircularProgressIndicator() // 显示加载指示器
        }

        Spacer(modifier = Modifier.height(20.dp))

        LoginAndRegisterButton(text = "返回") {
            navController.popBackStack()
        }
    }
}
