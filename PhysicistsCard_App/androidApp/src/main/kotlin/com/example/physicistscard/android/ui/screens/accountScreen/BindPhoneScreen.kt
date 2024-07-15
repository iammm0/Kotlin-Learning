package com.example.physicistscard.android.ui.screens.accountScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.inputs.LoginAndRegisterOutlinedTextField
import com.example.physicistscard.android.ui.components.buttons.LoginAndRegisterButton
import com.example.physicistscard.android.ui.components.icons.AppLogo
import com.example.physicistscard.android.ui.components.inputs.RowWithTextFieldAndButton
import com.example.physicistscard.android.ui.components.layouts.TopAppBar

@Composable
fun BindPhoneScreen(navController: NavController) {
    val phoneNumber = remember { mutableStateOf("") }
    var phoneCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar("绑定手机")
        Spacer(modifier = Modifier.height(30.dp))
        AppLogo()
        Spacer(modifier = Modifier.height(30.dp))
        LoginAndRegisterOutlinedTextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            labelText = "输入手机号码",
            isPassword = false,
            modifier = Modifier.width(330.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        RowWithTextFieldAndButton(
            textFieldValue = phoneCode,
            onTextFieldValueChange = { phoneCode = it },
            onButtonClick = { /*TODO*/ },
            textFieldLabel = "验证码",
            buttonText = "发送验证码"
        )
        Spacer(modifier = Modifier.height(42.dp))
        LoginAndRegisterButton(
            text = "绑定",
            onClick = {
                // Handle bind phone
            }
        )
    }
}
