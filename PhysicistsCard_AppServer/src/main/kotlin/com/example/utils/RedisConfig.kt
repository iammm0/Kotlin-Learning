package com.example.utils

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands
import com.typesafe.config.ConfigFactory

object RedisConfig {
    private val config = ConfigFactory.load()
    private val host = config.getString("ktor.services.redis.host")
    private val port = config.getInt("ktor.services.redis.port")
    private val password = config.getString("ktor.services.redis.password")

    private val redisUri = "redis://$password@$host:$port/0"
    private val client: RedisClient = RedisClient.create(redisUri)
    private val connection: StatefulRedisConnection<String, String> = client.connect()

    val commands: RedisCommands<String, String> = connection.sync()

    fun close() {
        connection.close()
        client.shutdown()
    }
}
