package com.example.physicistscard.transmissionModels.community.interaction.like

import kotlinx.serialization.Serializable

@Serializable
enum class LikeTargetType {
    POST, // 帖子
    PRODUCT // 商品
}
