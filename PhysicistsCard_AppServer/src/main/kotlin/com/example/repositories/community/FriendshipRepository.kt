package com.example.repositories.community

import com.example.models.databaseTableModels.community.interaction.Friendships
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class FriendshipRepository : IFriendshipRepository {

    override fun createFriendRequest(senderId: String, receiverId: String): Boolean {
        return transaction {
            // 检查是否已有好友请求或关系
            val existingFriendship = Friendships.selectAll().where {
                (Friendships.userId eq senderId and (Friendships.friendId eq receiverId)) or
                        (Friendships.userId eq receiverId and (Friendships.friendId eq senderId))
            }.count()

            if (existingFriendship > 0) {
                return@transaction false // 已存在请求或已是好友
            }

            // 插入好友请求，状态为PENDING
            Friendships.insert {
                it[userId] = senderId
                it[friendId] = receiverId
                it[status] = "PENDING"
            }.insertedCount > 0
        }
    }

    override fun updateFriendRequestStatus(userId: String, friendId: String, status: String): Boolean {
        return transaction {
            // 更新好友请求状态
            Friendships.update({ (Friendships.userId eq userId) and (Friendships.friendId eq friendId) }) {
                it[Friendships.status] = status
            } > 0
        }
    }

    override fun findFriends(userId: String): List<String> {
        return transaction {
            Friendships.selectAll().where {
                (Friendships.userId eq userId and (Friendships.status eq "ACCEPTED")) or
                        (Friendships.friendId eq userId and (Friendships.status eq "ACCEPTED"))
            }.map {
                if (it[Friendships.userId] == userId) it[Friendships.friendId]
                else it[Friendships.userId]
            }
        }
    }
}
