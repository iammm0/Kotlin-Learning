package com.example.models.databaseTableModels.community.interaction

import com.example.models.databaseTableModels.community.post.mPost.Posts
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption

object PostStats : UUIDTable() {
    val postId = reference("postId", Posts.id, onDelete = ReferenceOption.CASCADE)
    val likesCount = integer("likesCount").default(0)
    val commentsCount = integer("commentsCount").default(0)
    val favoritesCount = integer("favoritesCount").default(0)
}
