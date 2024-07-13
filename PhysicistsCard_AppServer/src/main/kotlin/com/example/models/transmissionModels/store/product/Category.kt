package com.example.models.transmissionModels.store.product

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val categoryId: String,
    val name: String,
    val description: String?
)