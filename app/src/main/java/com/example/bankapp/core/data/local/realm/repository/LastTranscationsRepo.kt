package com.example.bankapp.core.data.local.realm.repository

import com.example.bankapp.core.data.local.realm.model.FriendRealm
import com.example.bankapp.core.data.local.realm.model.UserRealm
import com.example.bankapp.core.data.remote.firebase.model.FriendFireStore
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore

interface LastTransactionsRepo<T> {
    fun getLoggedUser(userId: String): UserRealm?
    fun getAllUsers(): List<FriendRealm>
    fun add(item: T)
    fun update(item: T, userId: String)
    fun delete(item: T)
    fun deleteAllTransactions()
    fun replaceTransactions(user: UserFireStore)
    fun replaceFriends(friendFireStore: List<FriendFireStore>)
}