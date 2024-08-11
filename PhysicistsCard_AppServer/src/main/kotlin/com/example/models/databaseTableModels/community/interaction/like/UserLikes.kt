package com.example.models.databaseTableModels.community.interaction.like

import com.example.models.transmissionModels.community.interaction.LikeTargetType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserLikes : Table("userlikes") {
    val likeId = varchar("likeId", 50)
    val userId = varchar("userId", 50)
    val targetId = varchar("targetId", 50)
    val targetType = enumerationByName("targetType", 10, LikeTargetType::class)
    val createdAt = datetime("createdAt")

    override val primaryKey = PrimaryKey(likeId, name = "PK_UserLikes_LikeId")

    init {
        uniqueIndex(userId, targetId, targetType)  // 添加唯一性约束
    }
}