package com.example.models.transmissionModels.auth.merchant

import com.example.utils.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

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