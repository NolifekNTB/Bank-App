package com.example.bankapp.home.presentation.mvi

import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore

sealed class ViewIntent {
    data class LoadData(val userID: String = "") : ViewIntent()
    data class AddTransaction(val transaction: LastTransactionsFireStore) : ViewIntent()
}
