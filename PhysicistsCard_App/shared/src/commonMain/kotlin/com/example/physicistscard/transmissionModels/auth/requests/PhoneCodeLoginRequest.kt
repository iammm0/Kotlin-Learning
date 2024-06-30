package com.example.physicistscard.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class PhoneCodeLoginRequest(
    val phone: String,
    val phoneCode: String
) : LoginRequest()