package com.example.repositories.auth

import com.example.models.transmissionModels.auth.user.UserAddress

interface IUserAddressRepository {
    /**
     * 创建新的用户地址
     *
     * @param address 用户地址对象
     * @return 创建地址是否成功
     */
    fun createAddress(address: UserAddress): Boolean

    /**
     * 根据地址ID查找用户地址
     *
     * @param addressId 地址ID
     * @return 用户地址对象或null（如果未找到）
     */
    fun findAddressById(addressId: String): UserAddress?

    /**
     * 根据用户ID查找所有地址
     *
     * @param userId 用户ID
     * @return 用户地址列表
     */
    fun findAllAddressesByUserId(userId: String): List<UserAddress>

    /**
     * 更新用户地址
     *
     * @param addressId 地址ID
     * @param updatedAddress 更新后的用户地址对象
     * @return 更新地址是否成功
     */
    fun updateAddress(addressId: String, updatedAddress: UserAddress): Boolean

    /**
     * 删除用户地址
     *
     * @param addressId 地址ID
     * @return 删除地址是否成功
     */
    fun deleteAddress(addressId: String): Boolean
}
