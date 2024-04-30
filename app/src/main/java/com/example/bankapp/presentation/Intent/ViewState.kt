package com.example.bankapp.presentation.Intent

import com.example.bankapp.data.model.LastTranscations

sealed class ViewState {
    object Loading : ViewState()
    data class Success(val transactions: List<LastTranscations>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}

sealed class ViewIntent {
    data class AddTransaction(val transaction: LastTranscations) : ViewIntent()
    object LoadTransactions : ViewIntent()
}