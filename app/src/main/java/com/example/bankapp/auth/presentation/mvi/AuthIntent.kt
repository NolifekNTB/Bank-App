package com.example.bankapp.auth.presentation.mvi

sealed class AuthIntent {
    data class Login(val email: String, val password: String) : AuthIntent()
    data class Register(val email: String, val password: String) : AuthIntent()
}
