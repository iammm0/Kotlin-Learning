package com.example.physicistscard.android.ui.screens.authScreens

import AuthViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.buttons.LoginAndRegisterButton
import com.example.physicistscard.android.ui.components.icons.AppLogo
import com.example.physicistscard.android.ui.components.inputs.LoginAndRegisterOutlinedTextField
import com.example.physicistscard.android.ui.components.inputs.RowWithTextFieldAndButton
import com.example.physicistscard.businessLogic.IAuthService
import kotlinx.coroutines.launch

@Composable
fun LoginEmailScreen(navController: NavController, authService: IAuthService) {
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authService))

    var email by remember { mutableStateOf("") }
    var emailCode by remember { mutableStateOf("") }
    val loginStatus = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(200.dp))
        LoginAndRegisterOutlinedTextField(
            value = email,
            onValueChange = { email = it },
            labelText = "邮箱地址",
            isPassword = false,
            modifier = Modifier.width(330.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        RowWithTextFieldAndButton(
            textFieldValue = emailCode,
            onTextFieldValueChange = { emailCode = it },
            onButtonClick = {
                coroutineScope.launch {
                    viewModel.sendVerificationCode(email)
                }
            },
            textFieldLabel = "邮件代码",
            buttonText = "发送验证代码"
        )
        Spacer(modifier = Modifier.height(42.dp))
        LoginAndRegisterButton(text = "登录") {
            coroutineScope.launch {
                viewModel.loginWithEmailCode(email, emailCode)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(loginStatus.toString())
    }
}

class AuthViewModelFactory(private val authService: IAuthService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
