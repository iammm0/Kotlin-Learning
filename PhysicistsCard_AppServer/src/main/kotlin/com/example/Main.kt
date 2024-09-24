package com.example

import at.favre.lib.crypto.bcrypt.BCrypt

fun main() {
    val password = "password1"
    val passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())
    println(passwordHash)
}
