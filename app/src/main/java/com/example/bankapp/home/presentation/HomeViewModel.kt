package com.example.bankapp.home.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore
import com.example.bankapp.auth.data.repository.FirebaseUserRepositoryImpl
import com.example.bankapp.home.data.repository.LastTransactionsImpl
import com.example.bankapp.home.domain.HomeUseCase
import com.example.bankapp.home.presentation.mvi.ViewIntent
import com.example.bankapp.home.presentation.mvi.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repoRealm: LastTransactionsImpl,
    private val homeUseCase: HomeUseCase
): ViewModel() {
    private val _state = MutableStateFlow<ViewState>(ViewState.Loading)
    val state: StateFlow<ViewState> = _state.asStateFlow()

    suspend fun processIntent(intent: ViewIntent) {
        viewModelScope.launch {
            when (intent) {
                is ViewIntent.LoadData -> loadData(intent.userID)
                is ViewIntent.AddTransaction -> addNewTransaction(intent.transaction)
            }
        }
    }

    private suspend fun loadData(userId: String) {
        _state.value = ViewState.Loading
        val result = homeUseCase.loadData(userId)

        _state.value = if (result.isSuccess) {
            val cachedUserData = repoRealm.getLoggedUser(userId)
            val cachedFriends = repoRealm.getAllUsers()
            ViewState.DataLoaded(cachedUserData, cachedFriends)
        } else {
            ViewState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }

    private suspend fun addNewTransaction(transaction: LastTransactionsFireStore) {
        val result = homeUseCase.addNewTransaction(transaction)

        _state.value = if(result.isSuccess){
            ViewState.DataLoaded()
        } else {
            ViewState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}