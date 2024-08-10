package com.example.repositories.community

interface Repository<T, ID> {
    fun add(item: T): T
    fun findById(id: ID): T?
    fun findAll(): List<T?>
    fun update(item: T): T
    fun delete(id: ID): Boolean
}
