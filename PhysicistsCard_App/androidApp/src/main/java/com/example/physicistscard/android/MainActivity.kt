package com.example.physicistscard.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.physicistscard.android.ui.components.SystemUiController
import com.example.physicistscard.android.ui.screens.mainScreens.MainScreen
import com.example.physicistscard.android.ui.themes.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra("EXTRA_USER_ID") // 根据实际键名和数据类型获取
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    SystemUiController() // 调用来隐藏状态栏
                    MainScreen()
                }
            }
        }
    }
}