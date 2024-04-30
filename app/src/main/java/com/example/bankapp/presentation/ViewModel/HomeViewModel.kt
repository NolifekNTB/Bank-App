package com.example.bankapp.presentation.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.data.model.LastTranscations
import com.example.bankapp.domain.repository.LastTranscationsImpl
import com.example.bankapp.presentation.Intent.ViewIntent
import com.example.bankapp.presentation.Intent.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val repo = LastTranscationsImpl()

    private val _state = MutableStateFlow<ViewState>(ViewState.Loading)
    val state: StateFlow<ViewState> = _state.asStateFlow()

    init {
        processIntent(ViewIntent.LoadTransactions)
    }

    fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.LoadTransactions -> loadTransactions()
            is ViewIntent.AddTransaction -> addTransaction(intent.transaction)
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            try {
                val transactions = repo.getAll()
                _state.value = ViewState.Success(transactions)
            } catch (e: Exception) {
                _state.value = ViewState.Error(e)
            }
        }
    }

    private fun addTransaction(transaction: LastTranscations) {
        viewModelScope.launch {
            try {
                repo.add(transaction)
                loadTransactions()
            } catch (e: Exception) {
                _state.value = ViewState.Error(e)
            }
        }
    }
}