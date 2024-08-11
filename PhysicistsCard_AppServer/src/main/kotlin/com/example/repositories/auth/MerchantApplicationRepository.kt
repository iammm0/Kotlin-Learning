package com.example.repositories.auth

import com.example.models.databaseTableModels.auth.user.MerchantApplications
import com.example.models.transmissionModels.auth.requests.ApplicationStatus
import com.example.models.transmissionModels.auth.merchant.MerchantApplication
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime
import java.util.*

class MerchantApplicationRepository : IMerchantApplicationRepository {

    override fun saveApplication(application: MerchantApplication): Boolean {
        return try {
            transaction {
                MerchantApplications.insert {
                    it[applicationId] = UUID.randomUUID().toString()
                    it[userId] = application.userId
                    it[companyName] = application.companyName
                    it[contactNumber] = application.contactNumber
                    it[address] = application.address
                    it[licenseUrl] = application.licenseUrl
                    it[applicationStatus] = ApplicationStatus.PENDING
                    it[createdAt] = application.createdAt
                }
            }
            true
        } catch (e: Exception) {
            println("保存商家申请失败: ${e.localizedMessage}")
            false
        }
    }

    override fun findApplicationByUserId(userId: String): MerchantApplication? {
        return transaction {
            MerchantApplications.selectAll().where { MerchantApplications.userId eq userId }
                .mapNotNull {
                    MerchantApplication(
                        applicationId = it[MerchantApplications.applicationId],
                        userId = it[MerchantApplications.userId],
                        companyName = it[MerchantApplications.companyName],
                        contactNumber = it[MerchantApplications.contactNumber],
                        address = it[MerchantApplications.address],
                        licenseUrl = it[MerchantApplications.licenseUrl],
                        applicationStatus = it[MerchantApplications.applicationStatus],
                        createdAt = it[MerchantApplications.createdAt],
                        updatedAt = it[MerchantApplications.updatedAt]
                    )
                }
                .singleOrNull()
        }
    }

    override fun updateApplicationStatus(userId: String, status: ApplicationStatus): Boolean {
        return try {
            transaction {
                MerchantApplications.update({ MerchantApplications.userId eq userId }) {
                    it[applicationStatus] = status
                    it[updatedAt] = LocalDateTime.now()
                }
            } > 0
        } catch (e: Exception) {
            println("更新商家申请状态失败: ${e.localizedMessage}")
            false
        }
    }
}
