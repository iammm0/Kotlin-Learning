package com.example.physicistscard.transmissionModels.store.bag


// 用于临时存储用户想要购买的商品信息
data class StoreBag(
    val bagId: String,
    val userId: String,
    val items: List<BagItem>
)