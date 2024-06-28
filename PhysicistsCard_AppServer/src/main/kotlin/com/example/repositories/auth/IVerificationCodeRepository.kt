package com.example.repositories.auth

import com.example.models.transmissionModels.auth.VerificationType


interface IVerificationCodeRepository {
    fun saveVerificationCode(code: String, identifier: String, validityDuration: Long, type: VerificationType): Boolean
    fun verifyCode(identifier: String, code: String): Boolean
    fun deleteExpiredVerificationCodes(): Boolean
}
