package com.example.physicistscard.android.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.physicistscard.android.ui.components.navigation.AuthNavigation
import com.example.physicistscard.android.mainScreens.MainScreen
import com.example.physicistscard.android.ui.themes.MyApplicationTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import AuthViewModel

class MainActivity : ComponentActivity() {

    // 定义需要请求的权限
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.CAMERA
    )

    // 使用 Koin 注入 ViewModel
    private val authViewModel: AuthViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 启用 Edge-to-Edge 模式
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 设置内容视图
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // 根据是否已登录决定显示哪个屏幕
                    if (authViewModel.) {
                        MainScreen(navController)  // 登录后的主界面
                    } else {
                        AuthNavigation(authViewModel)  // 未登录时显示的认证界面（如登录/注册）
                    }
                }
            }
        }

        // 检查和请求权限
        if (!allPermissionsGranted()) {
            requestPermissionsLauncher.launch(REQUIRED_PERMISSIONS)
        } else {
            onPermissionsGranted()
        }
    }

    // 检查是否授予所有必要的权限
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    // 权限授予后的处理逻辑
    private fun onPermissionsGranted() {
        // 所有权限已授予，可以执行需要权限的操作
    }

    // 权限请求被拒绝后的处理逻辑
    private fun onPermissionsDenied() {
        // 处理权限被拒绝的情况，显示提示信息
        Toast.makeText(this, "Some permissions were denied", Toast.LENGTH_SHORT).show()
    }

    // 权限请求器
    private val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ ->
        if (allPermissionsGranted()) {
            onPermissionsGranted()
        } else {
            onPermissionsDenied()
        }
    }
}
