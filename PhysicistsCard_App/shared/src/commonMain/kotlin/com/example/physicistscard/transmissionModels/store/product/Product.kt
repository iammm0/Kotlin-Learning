package com.example.physicistscard.transmissionModels.store.product

import com.example.physicistscard.utils.LocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val productId: Int, // 商品Id
    val name: String, // 商品名称
    val description: Map<String, String>, // 商品描述，支持多语言
    val manufacturer: String?, // 制造商
    @Serializable(with = LocalDateSerializer::class)
    val releaseDate: LocalDate?, // 发布日期
    val price: Double, // 商品原价
    val promotionalPrice: Double?, // 促销价，如果不为空表示当前促销价格
    val isOnPromotion: Boolean = false, // 是否处于促销期
    val stockQuantity: Int, // 库存数量
    val imageUrls: List<String>, // 商品图片URL列表
    val tags: List<String>, // 商品标签，例如 "稀有", "限量"
    val era: Era, // 商品内容时期
    val physicist: List<Physicist>, // 与之相关联的著名物理学家，考虑到可能不止一个
    val physicsBranch: PhysicsBranch, // 商品所属物理学分支
    val bundleType: BundleType, // 商品售卖类型
    val ratings: Double?, // 商品评分，可以是平均值
    val reviewCount: Int? // 商品的评论数量
)