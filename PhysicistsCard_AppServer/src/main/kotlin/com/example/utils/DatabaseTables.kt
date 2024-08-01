package com.example.utils

import com.example.models.databaseTableModels.auth.authToken.RefreshTokens
import com.example.models.databaseTableModels.auth.user.UserAddresses
import com.example.models.databaseTableModels.auth.user.Users
import com.example.models.databaseTableModels.auth.verification.VerificationCodes
import com.example.models.databaseTableModels.store.bag.BagItems
import com.example.models.databaseTableModels.store.bag.StoreBags
import com.example.models.databaseTableModels.store.order.OrderHistories
import com.example.models.databaseTableModels.store.order.OrderItems
import com.example.models.databaseTableModels.store.order.Orders
import com.example.models.databaseTableModels.store.payment.PaymentInfos
import com.example.models.databaseTableModels.store.product.*
import com.example.models.databaseTableModels.store.shipping.ShippingInfos
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseTables {
    fun init() {
        transaction {
            // 清空数据库中的所有表
            // DatabaseCleaner.clearDatabase(
            //     Users,
            //     UserAddresses,
            //     VerificationCodes,
            //     Orders,
            //     OrderItems,
            //     OrderHistories,
            //     PaymentInfos,
            //     ShippingInfos,
            //     com.example.models.databaseTableModels.community.interaction.like.UserLikes,
            //     com.example.models.databaseTableModels.community.interaction.comment.UserComments,
            //     com.example.models.databaseTableModels.community.interaction.favorite.UserFavorites,
            //     com.example.models.databaseTableModels.community.post.content.Contents,
            //     com.example.models.databaseTableModels.community.post.content.TextContents,
            //     com.example.models.databaseTableModels.community.post.content.ImageContents,
            //     com.example.models.databaseTableModels.community.post.content.VideoContents,
            //     com.example.models.databaseTableModels.community.post.mPost.PostCategories,
            //     com.example.models.databaseTableModels.community.post.mPost.PostTags,
            //     com.example.models.databaseTableModels.community.post.mPost.Posts,
            //     com.example.models.databaseTableModels.community.post.mPost.PostTagRelations,
            //     Products,
            //     ProductVariants,
            //     ProductTags,
            //     ProductPhysicists,
            //     ProductDescriptions,
            //     StoreBags,
            //     BagItems,
            //     RefreshTokens
            // )

            // 创建无外键依赖的基本表
            // SchemaUtils.create(Users)
            // SchemaUtils.create(UserAddresses)
            // SchemaUtils.create(VerificationCodes)
            // SchemaUtils.create(Products)
            // SchemaUtils.create(com.example.models.databaseTableModels.community.post.mPost.PostCategories)
            // SchemaUtils.create(com.example.models.databaseTableModels.community.post.mPost.PostTags)
            // SchemaUtils.create(Orders)
            // // 创建依赖于 Users, Products, Orders 等基本表的内容表
            // SchemaUtils.create(OrderItems)  // 依赖 Orders
            // SchemaUtils.create(OrderHistories)  // 依赖 Orders
            // SchemaUtils.create(PaymentInfos)  // 依赖 Orders
            // SchemaUtils.create(ShippingInfos)  // 依赖 Orders
            // SchemaUtils.create(com.example.models.databaseTableModels.community.interaction.like.UserLikes)  // 依赖 Users 和 Posts（间接）
            // SchemaUtils.create(com.example.models.databaseTableModels.community.interaction.comment.UserComments)  // 依赖 Users 和 Posts（间接）
            // SchemaUtils.create(com.example.models.databaseTableModels.community.interaction.favorite.UserFavorites)  // 依赖 Users 和 Posts（间接）
            // SchemaUtils.create(RefreshTokens)
            // // 创建内容相关表
            // SchemaUtils.create(com.example.models.databaseTableModels.community.post.content.Contents)  // 依赖 Posts
            // SchemaUtils.create(com.example.models.databaseTableModels.community.post.content.TextContents)  // 依赖 Contents
            // SchemaUtils.create(com.example.models.databaseTableModels.community.post.content.ImageContents)  // 依赖 Contents
            // SchemaUtils.create(com.example.models.databaseTableModels.community.post.content.VideoContents)  // 依赖 Contents
            // // 创建标签关联表
            // SchemaUtils.create(
            //     com.example.models.databaseTableModels.community.post.mPost.PostTags,
            //     com.example.models.databaseTableModels.community.post.mPost.Posts,
            //     com.example.models.databaseTableModels.community.post.mPost.PostTagRelations
            // )
            // // 创建商品相关的附加表
            // SchemaUtils.create(ProductVariants)  // 依赖 Products
            // SchemaUtils.create(ProductTags)  // 依赖 Products
            // SchemaUtils.create(ProductPhysicists)  // 依赖 Products
            // SchemaUtils.create(ProductDescriptions)  // 依赖 Products
            // // 创建购物车相关表
            // SchemaUtils.create(StoreBags)  // 依赖 Users
            // SchemaUtils.create(BagItems)  // 依赖 StoreBags and Products
        }
    }
}
