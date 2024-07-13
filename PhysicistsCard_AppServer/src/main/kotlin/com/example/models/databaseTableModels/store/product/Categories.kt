package com.example.models.databaseTableModels.store.product

import org.jetbrains.exposed.sql.Table

object Categories : Table() {
    val categoryId = varchar("categoryId", 50)
    val name = varchar("name", 255) // 类别名称
    val description = text("description").nullable() // 类别描述

    override val primaryKey = PrimaryKey(categoryId, name = "PK_Category_Id")
}