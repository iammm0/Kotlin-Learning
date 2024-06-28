package com.example.physicistscard.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class EmailCodeLoginRequest(
    val email: String,
    val emailCode: String
) :LoginRequest()