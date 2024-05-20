package com.example.bankapp.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.auth.data.repository.FirebaseUserRepositoryImpl
import com.example.bankapp.auth.domain.AuthUseCases
import com.example.bankapp.auth.presentation.mvi.AuthIntent
import com.example.bankapp.auth.presentation.mvi.AuthViewState
import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _viewState = MutableStateFlow<AuthViewState>(AuthViewState.Idle)
    val viewState: StateFlow<AuthViewState> = _viewState.asStateFlow()

    init {
        checkCurrentUser()
    }

    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Login -> loginUser(intent.email, intent.password)
            is AuthIntent.Register -> registerUser(intent.email, intent.password)
        }
    }

    private fun checkCurrentUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        _viewState.value = if (currentUser != null) {
            AuthViewState.Success("User already logged in")
        } else {
            AuthViewState.Idle
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _viewState.value = AuthViewState.Loading
            val result = authUseCases.loginUser(email, password)
            _viewState.value = if (result.isSuccess) {
                AuthViewState.Success(result.getOrNull()!!)
            } else {
                AuthViewState.Error(result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _viewState.value = AuthViewState.Loading
            val result = authUseCases.registerUser(email, password)
            if (result.isSuccess) {
                createUserProfile()
            } else {
                _viewState.value = AuthViewState.Error(result.exceptionOrNull()?.message ?: "Registration failed")
            }
        }
    }

    private fun createUserProfile() {
        viewModelScope.launch {
            val result = authUseCases.createUserProfile()
            _viewState.value = if (result.isSuccess) {
                AuthViewState.Success(result.getOrNull()!!)
            } else {
                AuthViewState.Error(result.exceptionOrNull()?.message ?: "Profile creation failed")
            }
        }
    }

    /*fun logOut() {
        firebaseAuth.signOut()
    }

    override fun onCleared() {
        super.onCleared()
        firebaseAuth.removeAuthStateListener(authListener)
    }

     */
}