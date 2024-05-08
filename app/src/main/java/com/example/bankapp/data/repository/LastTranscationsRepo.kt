package com.example.bankapp.data.repository

abstract class LastTranscationsRepo<T> {
    abstract fun add(item: T)
    abstract fun update(item: T, userId: String)
    abstract fun delete(item: T)
    abstract fun deleteAll()
    abstract fun getAll(userId: String): List<T>
}