package com.example.repositories.auth

import com.example.models.transmissionModels.auth.verificationCodes.VerificationType


interface IVerificationCodeRepository {
    /**
     * 保存验证码
     *
     * @param code 验证码
     * @param identifier 用户标识符（邮箱或手机号）
     * @param validityDuration 验证码有效期（分钟）
     * @param type 验证码类型
     * @return 保存验证码是否成功
     */
    fun saveVerificationCode(code: String, identifier: String, validityDuration: Long, type: VerificationType): Boolean

    /**
     * 验证验证码
     *
     * @param identifier 用户标识符（邮箱或手机号）
     * @param code 验证码
     * @return 验证码是否有效
     */
    fun verifyCode(identifier: String, code: String): Boolean

    /**
     * 删除所有过期的验证码
     *
     * @return 删除操作是否成功
     */
    fun deleteExpiredVerificationCodes(): Boolean

    // 将markCodeAsUsed方法添加到接口中
    fun markCodeAsUsed(codeId: Int): Boolean

    /**
     * 验证验证码并将其标记为已使用
     *
     * @param identifier 用户标识符（邮箱或手机号）
     * @param code 验证码
     * @return 验证码是否有效并被标记为已使用
     */
    fun verifyAndMarkCodeAsUsed(identifier: String, code: String): Boolean

    // 根据 identifier 和 code 标记验证码为已使用
    fun markCodeAsUsed(identifier: String, code: String): Boolean
}
