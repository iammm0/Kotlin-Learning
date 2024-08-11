package com.example.models.databaseTableModels.auth.user

import com.example.models.databaseTableModels.auth.user.UserAddresses.addressId
import com.example.models.transmissionModels.auth.requests.ApplicationStatus
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object MerchantApplications : Table() {
    val applicationId = varchar("applicationId", 50)
    val userId = varchar("userId", 50).references(Users.userId)
    val companyName = varchar("companyName", 255)
    val contactNumber = varchar("contactNumber", 20)
    val address = varchar("address", 500)
    val licenseUrl = varchar("licenseUrl", 500)
    val applicationStatus = enumerationByName("applicationStatus", 50, ApplicationStatus::class)
    val createdAt = datetime("createdAt")
    val updatedAt = datetime("updatedAt").nullable()

    override val primaryKey = PrimaryKey(applicationId, name = "PK_MerchantApplications_merchantApplicationsId")
}
