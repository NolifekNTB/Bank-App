package com.example.bankapp.presentation.Intent

import com.example.bankapp.data.model.LastTransactions
import com.example.bankapp.data.model.User

sealed class ViewState {
    data object Loading : ViewState()
    data class DataLoaded(
        val user: User? = null,
        val allUsers: List<User> = emptyList()
    ) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}

sealed class ViewIntent {
    data class LoadData(val userID: String) : ViewIntent()
    data class addTransaction(val transaction: LastTransactions) : ViewIntent()
    /*
    data object LoadUserData : ViewIntent() // Intent for loading the logged-in user's data
    data object LoadTransactions : ViewIntent() // Intent for loading transactions
    data object LoadAllUsers : ViewIntent() // Intent for fetching all user profiles
    data class AddTransaction(val transaction: LastTransactions) : ViewIntent() // Intent for adding a new transaction
    data class UpdateTransaction(val transaction: LastTransactions, val userId: String) : ViewIntent()

     */
}

