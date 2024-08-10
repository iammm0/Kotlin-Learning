package com.example.utils

import com.example.models.databaseTableModels.community.post.content.Contents
import com.example.models.databaseTableModels.community.post.content.ImageContents
import com.example.models.databaseTableModels.community.post.content.TextContents
import com.example.models.databaseTableModels.community.post.content.VideoContents
import com.example.models.databaseTableModels.community.post.mPost.PostCategories
import com.example.models.databaseTableModels.community.post.mPost.PostTagRelations
import com.example.models.databaseTableModels.community.post.mPost.PostTags
import com.example.models.databaseTableModels.community.post.mPost.Posts
import org.jetbrains.exposed.sql.SchemaUtils.createMissingTablesAndColumns
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseTables {
    fun init() {
        // 使用 transaction 块确保数据库操作在同一个事务中进行
        transaction {
            // 自动创建或更新数据库表
            createMissingTablesAndColumns(
                PostCategories,
                Posts,
                Contents,
                ImageContents,
                TextContents,
                VideoContents,
                PostTags,
                PostTagRelations
            )
        }
    }
}
