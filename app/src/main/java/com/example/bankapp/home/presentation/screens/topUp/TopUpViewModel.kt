package com.example.bankapp.home.presentation.screens.topUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.R
import com.example.bankapp.home.presentation.screens.topUp.mvi.TopUpIntent
import com.example.bankapp.home.presentation.screens.topUp.mvi.TopUpState
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
            is TopUpIntent.SelectMethod -> _state.update { it.copy(selectedMethodOrPerson = intent.method) }
            is TopUpIntent.ChooseAmount -> _state.update { it.copy(chosenAmount = intent.amount) }
            is TopUpIntent.ConfirmIfWorks -> _state.update { it.copy(ifWorks = intent.ifWorks) }
            is TopUpIntent.SelectScreen -> _state.update { it.copy(chosenScreen = intent.screen) }
            is TopUpIntent.titlesDrawablesTexts -> _state.update { it.copy(
                titles = intent.titles,
                drawables = intent.drawables,
                texts = intent.texts)
            }
        }
    }

    //TODO: to state
    fun getPaymentMethodOrPersonIcon(): Int {
        val selectedMethod = _state.value.selectedMethodOrPerson

        return when (selectedMethod) {
            "Paypal" -> R.drawable.ic_paypal
            "Google Pay" -> R.drawable.ic_google_pay
            "Trustly" -> R.drawable.ic_trustly
            "Other E-Payment" -> R.drawable.ic_other_payment
            "Mastercard" -> R.drawable.ic_mastercard
            "Union Pay" -> R.drawable.ic_unionpay
            "Mike Mentzer" -> R.drawable.pictureprofile1
            "Mia Khalifa" -> R.drawable.pictureprofile2
            "Michael Scott" -> R.drawable.pictureprofile3
            else -> R.drawable.ic_google_pay
        }
    }

    fun titlesDrawablesTexts(){
        var titles = listOf<String>()
        var drawables = listOf<List<Int>>()
        var texts = listOf<List<String>>()

        when(_state.value.chosenScreen){
            "TopUp" -> {
                titles = listOf("E-Payment", "Credit Card")
                drawables = listOf(
                    listOf(R.drawable.ic_paypal, R.drawable.ic_google_pay,
                        R.drawable.ic_trustly, R.drawable.ic_other_payment),
                    listOf(R.drawable.ic_mastercard, R.drawable.ic_unionpay)
                )
                texts = listOf(
                    listOf("Paypal", "Google Pay",
                        "Trustly", "Other E-Payment"),
                    listOf("Mastercard", "Union Pay")
                )
            }
            "Transfer" -> {
                titles = listOf("Add new recipient", "Transfer list")
                drawables = listOf(
                    listOf(R.drawable.ic_bank, R.drawable.ic_bank),
                    listOf(R.drawable.pictureprofile1,
                        R.drawable.pictureprofile2,
                        R.drawable.pictureprofile3)
                )
                texts = listOf(
                    listOf("Add New Bank Account",
                        "Add New Credit Card"),
                    listOf("Mike Mentzer",
                        "John Doe",
                        "John Cena")
                )
            }
            "Widthdraw" -> {
                 //TODO
            }
            else -> listOf("")
        }

        if(titles.isNotEmpty() && drawables[0].isNotEmpty() && texts[0].isNotEmpty()) {
            handleIntent(TopUpIntent.titlesDrawablesTexts(titles, drawables, texts))
        }
    }

    fun fetchUserIDByName(name: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            topUpUseCase.fetchUserIDByName(name) { userId ->
                onResult(userId)
            }
        }
    }

    fun handleContinueButtonClick(name: String = "") {
        val chosenAmount = _state.value.chosenAmount ?: 0f

        if(_state.value.chosenScreen == "TopUp") {
            viewModelScope.launch {
                topUpUseCase.updateUserAccount(chosenAmount.toDouble()) { success ->
                    if (success) {
                        handleIntent(TopUpIntent.ConfirmIfWorks(true))
                    } else {
                        handleIntent(TopUpIntent.ConfirmIfWorks(false))
                    }
                }
            }
        }  else if (_state.value.chosenScreen == "Transfer") {
            fetchUserIDByName(name) { toUserID ->
                if(toUserID != null) {
                    viewModelScope.launch {
                        topUpUseCase.transferMoney(toUserID = toUserID, amount = chosenAmount.toDouble()) { success ->
                            if (success) {
                                handleIntent(TopUpIntent.ConfirmIfWorks(true))
                                Log.d("testowanie", "IfWorks: ${_state.value.ifWorks}")
                            } else {
                                handleIntent(TopUpIntent.ConfirmIfWorks(false))
                                Log.d("testowanie", "Ifworks: ${_state.value.ifWorks}")
                            }
                        }
                    }
                } else {
                    handleIntent(TopUpIntent.ConfirmIfWorks(false))
                }
            }
        }
    }
}