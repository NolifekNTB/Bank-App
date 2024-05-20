package com.example.bankapp.home.domain

import com.example.bankapp.auth.data.repository.FirebaseUserRepositoryImpl
import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore
import com.example.bankapp.home.data.repository.LastTransactionsImpl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class HomeUseCase(
    private val repoRealm: LastTransactionsImpl,
    private val repoFirebase: FirebaseUserRepositoryImpl
) {
    suspend fun loadData(userId: String): Result<String> {
        val cachedUserData = repoRealm.getLoggedUser(userId)
        val cachedFriends = repoRealm.getAllUsers()

        if (cachedUserData != null && cachedUserData.userId.isNotEmpty()) {
            return Result.success("Data loaded from cache")
        }

        return try {
            val userDeferred = CoroutineScope(Dispatchers.IO).async { repoFirebase.fetchUser(userId) }
            val allUsersDeferred = CoroutineScope(Dispatchers.IO).async { repoFirebase.fetchProfiles() }

            val user = userDeferred.await()
            val users = allUsersDeferred.await()

            if (user != null) {
                repoRealm.deleteAllTransactions()
                repoRealm.replaceTransactions(user)
                repoRealm.replaceFriends(users)
                Result.success("Data loaded successfully")
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addNewTransaction(transaction: LastTransactionsFireStore): Result<String> {
        return try {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: throw Exception("User not authenticated")
            repoFirebase.addTransaction(transaction, userId)
            Result.success("Transaction added successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}