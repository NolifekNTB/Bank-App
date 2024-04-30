package com.example.bankapp.domain.repository

abstract class LastTranscationsRepo<T> {
    abstract fun add(item: T)
    abstract fun update(item: T)
    abstract fun delete(item: T)
    abstract fun deleteAll()
    abstract fun getAll(): List<T>
}