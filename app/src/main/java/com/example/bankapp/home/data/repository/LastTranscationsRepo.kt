package com.example.bankapp.home.data.repository

import com.example.bankapp.core.data.local.realm.UserRealm

abstract class LastTranscationsRepo<T> {
    abstract fun getLoggedUser(userId: String): UserRealm?
    abstract fun getAllUsers(userId: String): List<T>
    abstract fun add(item: T)
    abstract fun update(item: T, userId: String)
    abstract fun delete(item: T)
    abstract fun deleteAll()
}