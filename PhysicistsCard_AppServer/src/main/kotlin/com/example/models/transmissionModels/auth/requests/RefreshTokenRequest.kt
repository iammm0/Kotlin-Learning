package com.example.models.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(val refreshToken: String)
