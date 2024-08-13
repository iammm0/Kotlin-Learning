package com.example.physicistscard.transmissionModels.auth

import kotlinx.serialization.Serializable

@Serializable
data class MerchantApplicationRequest(
    val companyName: String,
    val contactNumber: String,
    val address: String,
    val licenseUrl: String
)
