package com.example.repositories.store

import com.example.models.databaseTableModels.store.product.Categories
import com.example.models.transmissionModels.store.product.Category
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CategoryRepository : ICategoryRepository {
    override fun add(item: Category): Category {
        transaction {
            addLogger(StdOutSqlLogger)
            Categories.insert {
                it[categoryId] = item.categoryId
                it[name] = item.name
                it[description] = item.description
            }
        }
        return item
    }

    override fun findById(id: String): Category? {
        return transaction {
            addLogger(StdOutSqlLogger)
            Categories.selectAll().where { Categories.categoryId eq id }
                .mapNotNull {
                    Category(
                        categoryId = it[Categories.categoryId],
                        name = it[Categories.name],
                        description = it[Categories.description]
                    )
                }.singleOrNull()
        }
    }

    override fun findAll(): List<Category?> {
        return transaction {
            addLogger(StdOutSqlLogger)
            Categories.selectAll().map {
                Category(
                    categoryId = it[Categories.categoryId],
                    name = it[Categories.name],
                    description = it[Categories.description]
                )
            }
        }
    }

    override fun update(item: Category): Category {
        transaction {
            addLogger(StdOutSqlLogger)
            Categories.update({ Categories.categoryId eq item.categoryId }) {
                it[name] = item.name
                it[description] = item.description
            }
        }
        return item
    }

    override fun delete(id: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            Categories.deleteWhere { categoryId eq id } > 0
        }
    }
}
