package com.example.physicistscard.android.ui.components.inputs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowWithTextFieldAndButton(
    textFieldValue: String,
    onTextFieldValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    textFieldLabel: String,
    buttonText: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onTextFieldValueChange,
            label = { Text(
                textFieldLabel,
                style = TextStyle(
                    fontSize = 12.sp, // 设置字体大小
                    color = MaterialTheme.colorScheme.secondary // 设置字体颜色
                )
            ) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary, // 获得焦点时的边框颜色
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary // 未获得焦点时的边框颜色
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Spacer(modifier = Modifier.weight(0.1f))

        Button(
            onClick = onButtonClick,
            modifier = Modifier.align(Alignment.CenterVertically),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary), // 设置边框颜色和宽度
            shape = RoundedCornerShape(16.dp), // 设置按钮的圆角
        ) {
            Text(
                buttonText,
                style = TextStyle(
                    fontSize = 14.sp, // 设置字体大小
                    color = MaterialTheme.colorScheme.secondary // 设置字体颜色
                )
            )
        }
    }
}
