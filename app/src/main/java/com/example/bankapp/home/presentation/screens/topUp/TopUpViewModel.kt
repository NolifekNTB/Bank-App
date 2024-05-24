package com.example.bankapp.home.presentation.screens.topUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.auth.data.repository.FirebaseUserRepositoryImpl
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class TopUpViewModel(
    private val repoFirebase: FirebaseUserRepositoryImpl
): ViewModel() {
    private val userID = Firebase.auth.currentUser?.uid

    private suspend fun fetchUser(): UserFireStore {
        val userDeferred = CoroutineScope(Dispatchers.IO).async { repoFirebase.fetchUser(userID.toString()) }
        val data = userDeferred.await()
        return data!!
    }

    suspend fun updateUserAccount(amount: Double, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            try {
                repoFirebase.changeAccountBalance(userID.toString(), amount)
                onResult(true)
            } catch (e: Exception) {
                Log.e("TopUpViewModel", "Error updating user balance", e)
                onResult(false)
            }
        }
    }
}