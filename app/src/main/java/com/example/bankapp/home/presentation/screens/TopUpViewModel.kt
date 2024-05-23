package com.example.bankapp.home.presentation.screens

import androidx.lifecycle.ViewModel
import com.example.bankapp.auth.data.repository.FirebaseUserRepositoryImpl
import com.example.bankapp.auth.presentation.AuthViewModel
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.koin.androidx.compose.getViewModel


class TopUpViewModel(
    private val repoFirebase: FirebaseUserRepositoryImpl
): ViewModel() {
    private val userID = Firebase.auth.currentUser?.uid

    private suspend fun fetchUser(): UserFireStore {
        val userDeferred = CoroutineScope(Dispatchers.IO).async { repoFirebase.fetchUser(userID.toString()) }
        val data = userDeferred.await()
        return data!!
    }

    suspend fun updateUserAccount(amount: Double){
        val data = fetchUser()
        val newAmount = data.balance + amount
        repoFirebase.changeAccountBalance(userID.toString(), newAmount)
    }
}