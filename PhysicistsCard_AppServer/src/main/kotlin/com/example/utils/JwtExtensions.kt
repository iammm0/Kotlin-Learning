package com.example.utils

import com.example.models.transmissionModels.auth.user.Role
import io.ktor.server.auth.jwt.*

/**
 * 检查JWTPrincipal是否具有指定角色
 *
 * @param role 角色
 * @return 是否具有指定角色
 */
fun JWTPrincipal.hasRole(role: Role): Boolean {
    return this.payload.getClaim("role").asString() == role.name
}

/**
 * 检查JWTPrincipal是否具有任意一个指定角色
 *
 * @param roles 角色列表
 * @return 是否具有任意一个指定角色
 */
fun JWTPrincipal.hasAnyRole(vararg roles: Role): Boolean {
    val userRole = this.payload.getClaim("role").asString()
    return roles.any { it.name == userRole }
}