package com.example.models.databaseTableModels.auth.user

import com.example.models.transmissionModels.auth.merchant.ApplicationStatus
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object MerchantApplications : Table() {
    val applicationId = varchar("applicationid", 50)
    val userId = varchar("userid", 50).references(Users.userId)
    val companyName = varchar("companyname", 255)
    val contactNumber = varchar("contactnumber", 20)
    val address = varchar("address", 500)
    val licenseUrl = varchar("licenseurl", 500)
    val applicationStatus = enumerationByName("applicationstatus", 50, ApplicationStatus::class)
    val createdAt = datetime("createdat")
    val updatedAt = datetime("updatedat").nullable()

    override val primaryKey = PrimaryKey(applicationId, name = "PK_MerchantApplications_merchantApplicationsId")
}
