package com.example.bankapp.auth.presentation.mvi

sealed class AuthViewState {
    object Idle : AuthViewState()
    object Loading : AuthViewState()
    data class Success(val message: String) : AuthViewState()
    data class Error(val message: String) : AuthViewState()
}
