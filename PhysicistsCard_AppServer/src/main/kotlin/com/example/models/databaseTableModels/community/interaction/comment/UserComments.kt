package com.example.models.databaseTableModels.community.interaction.comment

import com.example.models.transmissionModels.community.interaction.CommentTargetType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserComments : Table("UserComments") {
    val commentId = varchar("commentId", 50)
    val userId = varchar("userId", 50)
    val targetId = varchar("targetId", 50)
    val targetType = enumerationByName("targetType", 10, CommentTargetType::class)
    val content = text("content")
    val createdAt = datetime("createdAt")
    val parentId = varchar("parentId", 50).nullable() // 可以为空，表示顶级评论

    override val primaryKey = PrimaryKey(commentId, name = "PK_UserComments_CommentId")
}
