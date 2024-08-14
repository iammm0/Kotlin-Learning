package com.example.physicistscard.android.activities

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
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.physicistscard.android.viewModels.CommunityViewModel
import com.example.physicistscard.android.viewModels.FriendshipViewModel
import com.example.physicistscard.android.viewModels.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    // 根据系统版本选择需要的权限
    private val REQUIRED_PERMISSIONS = mutableListOf(
        Manifest.permission.INTERNET,
        Manifest.permission.CAMERA
    ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            addAll(
                listOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
                )
            )
        }
    }.toTypedArray()

    private val friendshipViewModel: FriendshipViewModel by viewModel()
    private val communityViewModel: CommunityViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 根据实际键名和数据类型获取Intent中的额外数据
        intent.getStringExtra("EXTRA_USER_ID")

        // 设置内容视图
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // 隐藏状态栏
                    SystemUiController()
                    // 显示主屏幕
                    MainScreen(navController, friendshipViewModel)
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
    private val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (allPermissionsGranted()) {
            // 所有权限已被授予
            onPermissionsGranted()
        } else {
            // 处理权限被拒绝的情况
            onPermissionsDenied()
        }
    }

    private val pickVisualMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            // 用户选择了照片
            onPhotoSelected(uri)
        } else {
            // 用户未选择任何照片或操作被取消
            Toast.makeText(this, "No photo selected", Toast.LENGTH_SHORT).show()
        }
    }


    private fun onPhotoSelected(uri: Uri) {
        // 处理选择的照片URI，例如显示照片或上传到服务器
        // 这里可以使用Image组件显示照片，或将URI传递给其他功能模块
    }
}
