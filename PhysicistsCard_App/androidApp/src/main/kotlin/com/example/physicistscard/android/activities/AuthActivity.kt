package com.example.physicistscard.android.activities

import AuthViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.physicistscard.android.ui.components.SystemUiController
import com.example.physicistscard.android.ui.components.navigation.AuthNavigation
import com.example.physicistscard.android.ui.themes.MyApplicationTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class AuthActivity : ComponentActivity() {
    // 定义需要请求的权限
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE
    )

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SystemUiController() // 调用来隐藏状态栏
                    AuthNavigation(authViewModel)
                }
            }
        }

        // 检查所有权限是否已授予
        if (!allPermissionsGranted()) {
            // 如果没有被授予，则启动权限请求
            requestPermissionsLauncher.launch(REQUIRED_PERMISSIONS)
        } else {
            // 如果权限已被授予，执行相关逻辑
            onPermissionsGranted()
        }
    }

    // 检查所有需要的权限是否都已被授予
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    // 权限授予后的处理逻辑
    private fun onPermissionsGranted() {
        // 所有权限已被授予，可以初始化需要这些权限的功能
    }

    // 权限被拒绝后的处理逻辑
    private fun onPermissionsDenied() {
        // 处理权限被拒绝的情况，向用户显示解释为什么需要这些权限
    }

    // 权限请求器，用于请求多个权限
    private val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ ->
        if (allPermissionsGranted()) {
            // 所有权限已被授予
            onPermissionsGranted()
        } else {
            // 处理权限被拒绝的情况
            onPermissionsDenied()
        }
    }
}
