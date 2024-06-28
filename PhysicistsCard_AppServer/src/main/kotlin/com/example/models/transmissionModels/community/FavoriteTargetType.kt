package com.example.models.transmissionModels.community

import kotlinx.serialization.Serializable

@Serializable
enum class FavoriteTargetType {
    POST, // 帖子
    PRODUCT // 商品
}
