package com.example.models.databaseTableModels.community.interaction.favorite

import com.example.models.transmissionModels.community.interaction.FavoriteTargetType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserFavorites : Table("UserFavorites") {
    val favoriteId = varchar("favoriteId", 50)
    val userId = varchar("userId", 50)
    val targetId = varchar("targetId", 50)
    val targetType = enumerationByName("targetType", 10, FavoriteTargetType::class)
    val createdAt = datetime("createdAt")

    override val primaryKey = PrimaryKey(favoriteId, name = "PK_UserFavorites_FavoriteId")
}