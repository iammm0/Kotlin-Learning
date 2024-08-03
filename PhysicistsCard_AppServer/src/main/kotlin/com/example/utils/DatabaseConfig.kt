package com.example.utils

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.jetbrains.exposed.sql.Database

object DatabaseConfig {
    fun init() {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = "jdbc:postgresql://localhost:5432/physicists_card"
            username = "iammm"
            password = "iammm"
            maximumPoolSize = 3
            isAutoCommit = true
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        println("数据库连接完毕")
        val dataSource = HikariDataSource(config)

        // 初始化 Liquibase 并执行迁移
        dataSource.connection.use { connection ->
            val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(connection))
            val liquibase = Liquibase("liquibase/changelog-master.xml", ClassLoaderResourceAccessor(), database)
            liquibase.update("")
        }

        Database.connect(dataSource)
    }
}
