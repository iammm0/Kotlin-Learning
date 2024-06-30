package com.example.physicistscard.android.ui.screens.accountScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.inputs.LoginAndRegisterOutlinedTextField
import com.example.physicistscard.android.ui.components.buttons.LoginAndRegisterButton
import com.example.physicistscard.android.ui.components.inputs.RowWithTextFieldAndButton
import com.example.physicistscard.android.ui.components.layouts.TopAppBar

@Composable
fun BindEmailScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar("绑定邮箱")
        LoginAndRegisterOutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            labelText = "输入邮箱",
            isPassword = false
        )
        Spacer(modifier = Modifier.height(42.dp))
        LoginAndRegisterButton(
            text = "绑定",
            onClick = {
                // Handle bind email
            }
        )
    }
}
