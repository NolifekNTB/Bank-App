package com.example.bankapp.core.data.remote.firebase.repository

import com.example.bankapp.core.data.remote.firebase.model.FriendFireStore
import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore

interface FirebaseUserRepository {
    suspend fun createUserProfile(user: UserFireStore, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun fetchUser(userId: String): UserFireStore?
    suspend fun fetchProfiles(): List<FriendFireStore>
    suspend fun addTransaction(transaction: LastTransactionsFireStore, userID: String)
    suspend fun changeAccountBalance(userID: String, amount: Double)
    suspend fun transferMoney(fromUser: String, toUser: String, amount: Double)
}