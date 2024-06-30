package com.example.physicistscard.android.ui.screens.editScreens

import android.net.Uri
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPostScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "发表推送",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary
                    ) },
                actions = {

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            PostEditCard()
        }
    }
}

@Composable
fun PostEditCard() {
    var text by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var mediaUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // 创建一个用于选择图片的Launcher
    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // 处理选择的图片 URI
            mediaUri = uri
            Toast.makeText(context, "已选择图片: $uri", Toast.LENGTH_SHORT).show()
            // 可以在这里显示图片或进行其他操作
        } else {
            Toast.makeText(context, "没有图片被选择", Toast.LENGTH_SHORT).show()
        }
    }

    // 创建一个用于选择视频的Launcher
    val pickVideoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            // 处理选择的视频 URI
            mediaUri = uri
            Toast.makeText(context, "已选择视频: $uri", Toast.LENGTH_SHORT).show()
            // 可以在这里显示视频或进行其他操作
        } else {
            Toast.makeText(context, "没有视频被选择", Toast.LENGTH_SHORT).show()
        }
    }


    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(400.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(14.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth().weight(2f)) {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(
                                text = "大物理学家，请说点什么吧~",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 显示选中的图片或视频
                mediaUri?.let { uri ->
                    if (isImageUri(uri)) {
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color.Gray),
                            contentScale = ContentScale.Crop
                        )
                    } else if (isVideoUri(uri)) {
                        AndroidView(
                            factory = { context ->
                                VideoView(context).apply {
                                    setVideoURI(uri)
                                    start()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                // 底部添加图片或视频的按钮
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .size(48.dp) // 设置 IconButton 的大小
                        .background(
                            color = Color(0x9EB388FF),
                            shape = CircleShape
                        ) // 设置背景色和形状
                        .padding(12.dp) // 设置内边距
                    ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Image or Video", tint = MaterialTheme.colorScheme.secondary)
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("选择类型") },
                        text = { Text("请选择您要添加的内容类型。") },
                        containerColor  = MaterialTheme.colorScheme.background,
                        textContentColor = MaterialTheme.colorScheme.secondary,
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    pickImageLauncher.launch("image/*")
                                    showDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                            ) {
                                Text("图片")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    pickVideoLauncher.launch("video/*")
                                    showDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                            ) {
                                Text("视频")
                            }
                        }
                    )
                }
                Spacer(Modifier.width(14.dp))
                // 发表内容
                IconButton(
                    onClick = { /* 选择图片或视频的逻辑 */ },
                    modifier = Modifier
                        .size(48.dp) // 设置 IconButton 的大小
                        .background(
                            color = Color(0x9EB388FF),
                            shape = CircleShape
                        ) // 设置背景色和形状
                        .padding(12.dp) // 设置内边距
                ) {
                    Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Send Post", tint = MaterialTheme.colorScheme.secondary)
                }
            }
        }
    }
}

// 判断是否是图片 URI
private fun isImageUri(uri: Uri): Boolean {
    val mimeType = uri.toString()
    return mimeType.contains("image")
}

// 判断是否是视频 URI
private fun isVideoUri(uri: Uri): Boolean {
    val mimeType = uri.toString()
    return mimeType.contains("video")
}
