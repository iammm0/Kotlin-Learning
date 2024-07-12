package com.example.repositories.auth

import com.example.models.transmissionModels.auth.user.UserAddress

interface IUserAddressRepository {
    fun createAddress(address: UserAddress): Boolean
    fun findAddressById(addressId: String): UserAddress?
    fun findAllAddressesByUserId(userId: String): List<UserAddress>
    fun updateAddress(addressId: String, updatedAddress: UserAddress): Boolean
    fun deleteAddress(addressId: String): Boolean
}