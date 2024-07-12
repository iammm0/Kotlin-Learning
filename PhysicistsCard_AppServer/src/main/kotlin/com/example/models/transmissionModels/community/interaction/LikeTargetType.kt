package com.example.models.transmissionModels.community.interaction

import kotlinx.serialization.Serializable

@Serializable
enum class LikeTargetType {
    POST, // 帖子
    PRODUCT // 商品
}
