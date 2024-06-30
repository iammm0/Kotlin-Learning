package com.example.physicistscard.android.ui.screens.communityScreens.basicItems

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommentInputBar(onCommentSubmitted: (String) -> Unit,  onDismissRequest: () -> Unit) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var commentText by remember { mutableStateOf("") }

    // 当组件被添加到布局时，请求焦点并弹出软键盘
    LaunchedEffect(true) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    DisposableEffect(Unit) {
        onDispose {
            onDismissRequest()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            shape = RoundedCornerShape(28.dp), // 设置边框的圆角形状
            placeholder = {
                Text(
                    text = "说点什么...",
                    color = MaterialTheme.colorScheme.secondary,
                    style = TextStyle(fontSize = 12.sp) // 设置placeholder文本的字体大小
                    ) },
            modifier = Modifier
                .weight(1f)
                .height(48.dp) // 直接设置OutlinedTextField的高度
                .focusRequester(focusRequester), // 应用focusRequester
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary, // 获得焦点时的边框颜色
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary // 未获得焦点时的边框颜色
            ),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
            // 调用回调函数处理评论提交
            onCommentSubmitted(commentText)
            // 清空输入框
            commentText = ""
            // 提交评论后隐藏键盘
            keyboardController?.hide()
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0x9EB388FF)),
            modifier = Modifier.height(48.dp),
            ) {
            Text(
                text = "发送",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary,)
                )
        }
    }
}
