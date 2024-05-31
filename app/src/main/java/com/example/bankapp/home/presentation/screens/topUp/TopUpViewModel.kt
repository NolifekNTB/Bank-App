package com.example.bankapp.home.presentation.screens.topUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.R
import com.example.bankapp.auth.data.repository.FirebaseUserRepositoryImpl
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore
import com.example.bankapp.home.di.topUpModule
import com.example.bankapp.home.presentation.screens.topUp.mvi.TopUpIntent
import com.example.bankapp.home.presentation.screens.topUp.mvi.TopUpState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TopUpViewModel(
    private var topUpUseCase: TopUpUseCase
): ViewModel() {
    private val _state = MutableStateFlow(TopUpState())
    val state: StateFlow<TopUpState> get() = _state

    fun handleIntent(intent: TopUpIntent) {
        when (intent) {
            is TopUpIntent.SelectMethod -> _state.update { it.copy(selectedMethod = intent.method) }
            is TopUpIntent.ChooseAmount -> _state.update { it.copy(chosenAmount = intent.amount) }
            is TopUpIntent.ConfirmIfWorks -> _state.update { it.copy(ifWorks = intent.ifWorks) }
        }
    }

    fun getPaymentMethodIcon(): Int {
        val selectedMethod = _state.value.selectedMethod

        return when (selectedMethod) {
            "Paypal" -> R.drawable.ic_paypal
            "Google Pay" -> R.drawable.ic_google_pay
            "Trustly" -> R.drawable.ic_trustly
            "Other E-Payment" -> R.drawable.ic_other_payment
            "Mastercard" -> R.drawable.ic_mastercard
            "Union Pay" -> R.drawable.ic_unionpay
            else -> R.drawable.ic_google_pay
        }
    }

    fun handleContinueButtonClick() {
        val chosenAmount = _state.value.chosenAmount ?: 0f

        viewModelScope.launch {
            topUpUseCase.updateUserAccount(chosenAmount.toDouble()) { success ->
                if (success) {
                    handleIntent(TopUpIntent.ConfirmIfWorks(true))
                } else {
                    handleIntent(TopUpIntent.ConfirmIfWorks(false))
                }
            }
        }
    }
}