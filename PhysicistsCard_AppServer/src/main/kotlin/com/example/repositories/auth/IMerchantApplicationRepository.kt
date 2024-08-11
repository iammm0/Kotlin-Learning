package com.example.repositories.auth

import com.example.models.transmissionModels.auth.requests.ApplicationStatus
import com.example.models.transmissionModels.auth.merchant.MerchantApplication

interface IMerchantApplicationRepository {

    /**
     * 保存商家入驻申请信息。
     *
     * @param application 商家申请对象，包含用户提交的商家信息和申请状态。
     * @return 如果申请信息保存成功则返回 true，保存失败则返回 false。
     */
    fun saveApplication(application: MerchantApplication): Boolean

    /**
     * 根据用户ID查找商家入驻申请信息。
     *
     * @param userId 用户的唯一标识符。
     * @return 找到的商家申请对象。如果未找到，则返回 null。
     */
    fun findApplicationByUserId(userId: String): MerchantApplication?

    /**
     * 更新商家入驻申请的状态。
     *
     * @param userId 用户的唯一标识符，用于标识要更新的商家申请。
     * @param status 新的申请状态，例如 PENDING、APPROVED、REJECTED。
     * @return 如果状态更新成功则返回 true，更新失败则返回 false。
     */
    fun updateApplicationStatus(userId: String, status: ApplicationStatus): Boolean
}
