package com.example.physicistscard.transmissionModels.store.product

import kotlinx.serialization.Serializable

// 在电商平台上，一个商品（Product）可能有多个不同的版本或变体，这些变体可能在颜色、尺寸、配置等方面有所不同。
// ProductVariant模型就是用来详细描述这些不同变体的。
@Serializable
data class ProductVariant(
    val variantId: String, // 商品变体的唯一标识符
    val productId: String, // 这个字段指向这个变体所属的基础商品的ID
    val name: String, // 如 "限量版", "珍藏版"
    val stockQuantity: Int, // 变体的库存数量
    val additionalPrice: Double? // 与基础商品相比的附加价格 有些变体可能因为特殊材料、设计或其他因素而比基础商品贵
)