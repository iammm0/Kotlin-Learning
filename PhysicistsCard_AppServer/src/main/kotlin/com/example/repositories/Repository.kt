package com.example.repositories

interface Repository<T,Q> {
    fun add(item: T): T
    fun findById(id: Q): T?
    fun findAll(): List<T?>
    fun update(item: T): T
    fun delete(id: Q): Boolean
}