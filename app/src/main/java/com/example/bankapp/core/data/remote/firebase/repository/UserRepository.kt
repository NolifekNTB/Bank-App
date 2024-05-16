package com.example.bankapp.core.data.remote.firebase.repository

import com.example.bankapp.core.data.remote.firebase.model.FriendFireStore
import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore

interface UserRepository {
    fun createUserProfile(user: UserFireStore, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun fetchUserData(userId: String): UserFireStore?
    suspend fun fetchUserProfiles(): List<FriendFireStore>
    fun addTransaction(transaction: LastTransactionsFireStore, userID: String)
}