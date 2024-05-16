package com.example.bankapp.home.presentation.mvi

import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore

sealed class ViewIntent {
    data class LoadData(val userID: String = "") : ViewIntent()
    data class addTransaction(val transaction: LastTransactionsFireStore) : ViewIntent()
    /*
    data object LoadUserData : ViewIntent() // Intent for loading the logged-in user's data
    data object LoadTransactions : ViewIntent() // Intent for loading transactions
    data object LoadAllUsers : ViewIntent() // Intent for fetching all user profiles
    data class AddTransaction(val transaction: LastTransactions) : ViewIntent() // Intent for adding a new transaction
    data class UpdateTransaction(val transaction: LastTransactions, val userId: String) : ViewIntent()

     */
}
