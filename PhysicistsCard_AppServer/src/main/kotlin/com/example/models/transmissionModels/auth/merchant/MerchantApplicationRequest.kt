package com.example.models.transmissionModels.auth.merchant

import kotlinx.serialization.Serializable

@Serializable
data class MerchantApplicationRequest(
    val companyName: String,
    val contactNumber: String,
    val address: String,
    val licenseUrl: String
)
