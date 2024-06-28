package com.example.models.transmissionModels.store

import kotlinx.serialization.Serializable

// 支付方式
@Serializable
enum class PaymentMethod {
    WECHAT_PAY, // 微信支付
    ALIPAY, // 支付宝
    UNION_PAY // 银联
}