package com.example.physicistscard.android.viewModels

enum class OperationType(val value: String) {
    PHONE_REGISTER("phone_register"),
    PHONE_LOGIN("phone_login"),
    RESET_PASSWORD("reset_password"),
    EMAIL_REGISTER("email_register"),
    EMAIL_LOGIN("email_login"),
    BIND_EMAIL("bind_email"),
    BIND_PHONE("bind_phone")
}