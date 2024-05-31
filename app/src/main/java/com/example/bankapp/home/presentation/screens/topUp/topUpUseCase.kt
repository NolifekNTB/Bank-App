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
}