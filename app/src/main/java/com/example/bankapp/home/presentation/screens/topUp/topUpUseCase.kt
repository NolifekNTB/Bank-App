package com.example.bankapp.home.presentation.screens.topUp

import android.util.Log
import com.example.bankapp.auth.data.repository.FirebaseUserRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class TopUpUseCase(
    private val repoFirebase: FirebaseUserRepositoryImpl
) {
    private val userID = Firebase.auth.currentUser?.uid

    suspend fun updateUserAccount(amount: Double, onResult: (Boolean) -> Unit){
        try {
            repoFirebase.changeAccountBalance(userID.toString(), amount)
            onResult(true)
        } catch (e: Exception) {
            Log.e("TopUpViewModel", "Error updating user balance", e)
            onResult(false)
        }
    }

    suspend fun fetchUserIDByName(name: String, onResult: (String?) -> Unit) {
        try {
            val userId = repoFirebase.getUserIDByName(name)
            onResult(userId)
        } catch (e: Exception) {
            Log.e("TopUpUseCase", "Error fetching user ID by name", e)
            onResult(null)
        }
    }

    suspend fun transferMoney(toUserID: String, amount: Double, onResult: (Boolean) -> Unit) {
        val fromUser = userID.toString()
        try {
            repoFirebase.transferMoney(fromUser = fromUser, toUserID, amount)
            onResult(true)
        } catch (e: Exception) {
            Log.e("testowanie", "Error transferring money", e)
            onResult(false)
        }
    }
}