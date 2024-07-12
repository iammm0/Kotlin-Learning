package com.example.repositories.auth
import com.example.models.databaseTableModels.auth.user.UserAddresses
import com.example.models.transmissionModels.auth.user.UserAddress
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserAddressRepository : IUserAddressRepository {
    override fun createAddress(address: UserAddress): Boolean {
        return transaction {
            UserAddresses.insert {
                it[addressId] = address.addressId
                it[userId] = address.userId
                it[recipientName] = address.recipientName
                it[phoneNumber] = address.phoneNumber
                it[province] = address.province
                it[city] = address.city
                it[district] = address.district
                it[street] = address.street
                it[residentialCommunity] = address.residentialCommunity
                it[buildingNumber] = address.buildingNumber
                it[unitNumber] = address.unitNumber
                it[roomNumber] = address.roomNumber
                it[zipCode] = address.zipCode
            }.insertedCount > 0
        }
    }

    override fun findAddressById(addressId: String): UserAddress? {
        return transaction {
            UserAddresses.selectAll().where { UserAddresses.addressId eq addressId }
                .mapNotNull {
                    UserAddress(
                        addressId = it[UserAddresses.addressId],
                        userId = it[UserAddresses.userId],
                        recipientName = it[UserAddresses.recipientName],
                        phoneNumber = it[UserAddresses.phoneNumber],
                        province = it[UserAddresses.province],
                        city = it[UserAddresses.city],
                        district = it[UserAddresses.district],
                        street = it[UserAddresses.street],
                        residentialCommunity = it[UserAddresses.residentialCommunity],
                        buildingNumber = it[UserAddresses.buildingNumber],
                        unitNumber = it[UserAddresses.unitNumber],
                        roomNumber = it[UserAddresses.roomNumber],
                        zipCode = it[UserAddresses.zipCode]
                    )
                }.singleOrNull()
        }
    }

    override fun findAllAddressesByUserId(userId: String): List<UserAddress> {
        return transaction {
            UserAddresses.selectAll().where { UserAddresses.userId eq userId }
                .map {
                    UserAddress(
                        addressId = it[UserAddresses.addressId],
                        userId = it[UserAddresses.userId],
                        recipientName = it[UserAddresses.recipientName],
                        phoneNumber = it[UserAddresses.phoneNumber],
                        province = it[UserAddresses.province],
                        city = it[UserAddresses.city],
                        district = it[UserAddresses.district],
                        street = it[UserAddresses.street],
                        residentialCommunity = it[UserAddresses.residentialCommunity],
                        buildingNumber = it[UserAddresses.buildingNumber],
                        unitNumber = it[UserAddresses.unitNumber],
                        roomNumber = it[UserAddresses.roomNumber],
                        zipCode = it[UserAddresses.zipCode]
                    )
                }
        }
    }

    override fun updateAddress(addressId: String, updatedAddress: UserAddress): Boolean {
        return transaction {
            UserAddresses.update({ UserAddresses.addressId eq addressId }) {
                it[userId] = updatedAddress.userId
                it[recipientName] = updatedAddress.recipientName
                it[phoneNumber] = updatedAddress.phoneNumber
                it[province] = updatedAddress.province
                it[city] = updatedAddress.city
                it[district] = updatedAddress.district
                it[street] = updatedAddress.street
                it[residentialCommunity] = updatedAddress.residentialCommunity
                it[buildingNumber] = updatedAddress.buildingNumber
                it[unitNumber] = updatedAddress.unitNumber
                it[roomNumber] = updatedAddress.roomNumber
                it[zipCode] = updatedAddress.zipCode
            } > 0
        }
    }

    override fun deleteAddress(addressId: String): Boolean {
        return transaction {
            UserAddresses.deleteWhere { UserAddresses.addressId eq addressId } > 0
        }
    }
}
