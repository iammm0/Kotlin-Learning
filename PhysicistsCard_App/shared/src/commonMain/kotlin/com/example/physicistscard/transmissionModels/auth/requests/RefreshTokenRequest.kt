package com.example.physicistscard.transmissionModels.auth.requests

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(val refreshToken: String)