package com.example.models.transmissionModels.store.bag

import kotlinx.serialization.Serializable

@Serializable
data class BagItem(
    val productId: String,
    val quantity: Int
)