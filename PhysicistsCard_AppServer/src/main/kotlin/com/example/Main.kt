package com.example

import at.favre.lib.crypto.bcrypt.BCrypt

fun main() {
    val password = "SuperSecret"
    val passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())
    println(passwordHash)
}
