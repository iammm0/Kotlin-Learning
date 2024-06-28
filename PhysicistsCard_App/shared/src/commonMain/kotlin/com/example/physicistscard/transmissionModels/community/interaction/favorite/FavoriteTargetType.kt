package com.example.physicistscard.transmissionModels.community.interaction.favorite

import kotlinx.serialization.Serializable

@Serializable
enum class FavoriteTargetType {
    POST, // 帖子
    PRODUCT // 商品
}
