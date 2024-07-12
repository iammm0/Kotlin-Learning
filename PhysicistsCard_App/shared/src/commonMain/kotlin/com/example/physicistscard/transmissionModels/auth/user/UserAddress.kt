package com.example.physicistscard.transmissionModels.auth.user

import kotlinx.serialization.Serializable

@Serializable
data class UserAddress(
    val addressId: String,
    val userId: String,
    val recipientName: String, // 收件人姓名
    val phoneNumber: String, // 联系电话
    val province: String, // 省份
    val city: String, // 城市
    val district: String, // 区/县
    val street: String, // 街道
    val residentialCommunity: String?, // 小区/居住区，可选
    val buildingNumber: String?, // 楼栋号，可选
    val unitNumber: String?, // 单元号，可选
    val roomNumber: String?, // 房间/门牌号
    val zipCode: String // 邮编
)