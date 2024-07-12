package com.example.models.transmissionModels.store.bag

import kotlinx.serialization.Serializable


// 用于临时存储用户想要购买的商品信息
@Serializable
data class StoreBag(
    val bagId: String,
    val userId: String,
    val items: List<BagItem>
)