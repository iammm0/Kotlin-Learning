package com.example.physicistscard.android.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.buttons.LoginAndRegisterButton
import com.example.physicistscard.android.ui.components.icons.AppLogo
import com.example.physicistscard.android.ui.components.layouts.TopAppBar

@Composable
fun UnsubscribeAccountScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        TopAppBar("注销账户")
        Spacer(modifier = Modifier.height(30.dp))
        AppLogo()
        Spacer(modifier = Modifier.height(30.dp))
        LoginAndRegisterButton(
            text = "注销账户",
            onClick = {
                // Handle unsubscribe account
            },
        )
    }
}
