package com.example.physicistscard.android.ui.screens.authScreens

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.buttons.LoginAndRegisterButton
import com.example.physicistscard.android.ui.components.icons.AppLogo
import com.example.physicistscard.android.ui.components.inputs.LoginAndRegisterOutlinedTextField
import com.example.physicistscard.android.ui.components.inputs.RowWithTextFieldAndButton
import com.example.physicistscard.transmissionModels.auth.requests.RegistrationRequest
import com.example.physicistscard.transmissionModels.auth.user.Role
import kotlinx.coroutines.launch

@Composable
fun RegisterEmailScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailCode by remember { mutableStateOf("") }

    // 监听注册状态
    val registerState by authViewModel.registerState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(135.dp))

        // 邮箱输入框
        LoginAndRegisterOutlinedTextField(
            value = email,
            onValueChange = { email = it },
            labelText = "邮件地址",
            isPassword = false,
            modifier = Modifier.width(330.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 密码输入框
        LoginAndRegisterOutlinedTextField(
            value = password,
            onValueChange = { password = it },
            labelText = "密码",
            isPassword = true,
            modifier = Modifier.width(330.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 确认密码输入框
        LoginAndRegisterOutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            labelText = "确认密码",
            isPassword = true,
            modifier = Modifier.width(330.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 邮件验证码输入框
        RowWithTextFieldAndButton(
            textFieldValue = emailCode,
            onTextFieldValueChange = { emailCode = it },
            onButtonClick = {
                authViewModel.sendVerificationCode(email, "email_register")
            },
            textFieldLabel = "邮件代码",
            buttonText = "发送验证代码"
        )
        Spacer(modifier = Modifier.height(32.dp))

        // 注册按钮
        LoginAndRegisterButton(text = "完成注册") {
            if (password == confirmPassword) {
                authViewModel.registerUser(
                    RegistrationRequest(
                        email = email,
                        password = password,
                        code = emailCode,
                        role = Role.USER
                    )
                )
            } else {
                // 处理密码不匹配的情况
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 处理注册状态
        registerState?.let { result ->
            when {
                result.isSuccess -> {
                    Text("注册成功")
                    navController.navigate("login_screen") // 成功后导航到登录界面
                }
                result.isFailure -> {
                    val exception = result.exceptionOrNull()
                    Text(
                        text = "注册失败: ${exception?.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}


