package com.example.physicistscard.transmissionModels.auth

import com.example.physicistscard.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MerchantApplication(
    val applicationId: String,
    val userId: String,
    val companyName: String,
    val contactNumber: String,
    val address: String,
    val licenseUrl: String,
    val applicationStatus: ApplicationStatus,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime? = null
)