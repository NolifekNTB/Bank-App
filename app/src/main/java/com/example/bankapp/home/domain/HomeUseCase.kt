package com.example.bankapp.home.domain

import android.util.Log
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
                Log.e("HomeUseCase", "User not found, falling back to cache")
                loadFromCache(userId)
            }
        }  catch (e: Exception) {
            Log.e("HomeUseCase", "Error loading data from Firebase, falling back to cache", e)
            loadFromCache(userId)
        }
    }

    private fun loadFromCache(userId: String): Result<String> {
        return try {
            val cachedUserData = repoRealm.getLoggedUser(userId)
            val cachedFriends = repoRealm.getAllUsers()

            if (cachedUserData != null && cachedUserData.userId.isNotEmpty()) {
                Result.success("Data loaded from cache")
            } else {
                Result.failure(Exception("No cached data available"))
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