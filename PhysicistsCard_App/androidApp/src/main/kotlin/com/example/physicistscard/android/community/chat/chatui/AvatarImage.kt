package com.example.physicistscard.android.community.chat.chatui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.physicistscard.android.R

@Composable
fun AvatarImage(avatarUrl: String?, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    val painter = if (avatarUrl.isNullOrEmpty()) {
        painterResource(id = R.drawable.ic_profile) // 你的默认头像资源
    } else {
        rememberAsyncImagePainter(model = avatarUrl)
    }

    Image(
        painter = painter,
        contentDescription = "Avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Gray)
            .clickable { onClick() }
    )
}