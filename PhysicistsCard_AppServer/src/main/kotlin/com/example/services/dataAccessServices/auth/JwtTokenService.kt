package com.example.services.dataAccessServices.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.example.models.transmissionModels.auth.user.User
import java.util.*

class JwtTokenService(
    private val secret: String,
    private val issuer: String,
    private val validityInMs: Long
) : TokenService {

    private val algorithm: Algorithm = Algorithm.HMAC256(secret)

    private val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .build()

    override fun generateToken(user: User): String {
        return JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("userId", user.userId)
            .withClaim("role", user.role.name) // 包含角色信息
            .withExpiresAt(Date(System.currentTimeMillis() + validityInMs))
            .sign(algorithm)
    }

    override fun verifyToken(token: String): Boolean {
        return try {
            val decodedJWT: DecodedJWT = verifier.verify(token)
            decodedJWT.expiresAt.after(Date())
        } catch (exception: JWTVerificationException) {
            false
        }
    }

    override fun refreshToken(token: String): String? {
        return try {
            val decodedJWT: DecodedJWT = verifier.verify(token)
            val userId = decodedJWT.getClaim("userId").asString()
            val role = decodedJWT.getClaim("role").asString()
            JWT.create()
                .withSubject("Authentication")
                .withIssuer(issuer)
                .withClaim("userId", userId)
                .withClaim("role", role)
                .withExpiresAt(Date(System.currentTimeMillis() + validityInMs))
                .sign(algorithm)
        } catch (exception: JWTVerificationException) {
            null
        }
    }
}
