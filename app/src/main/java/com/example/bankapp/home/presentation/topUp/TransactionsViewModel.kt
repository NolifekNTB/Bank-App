package com.example.bankapp.home.presentation.Transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.R
import com.example.bankapp.home.domain.TransactionsUseCase
import com.example.bankapp.home.presentation.Transactions.mvi.TransactionsIntent
import com.example.bankapp.home.presentation.Transactions.mvi.TransactionsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TransactionsViewModel(
    private var transactionsUseCase: TransactionsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(TransactionsState())
    val state: StateFlow<TransactionsState> get() = _state

    fun handleIntent(intent: TransactionsIntent) {
        when (intent) {
            is TransactionsIntent.SelectMethod -> updateState { it.copy(selectedMethodOrPerson = intent.method) }
            is TransactionsIntent.ChooseAmount -> updateState { it.copy(chosenAmount = intent.amount) }
            is TransactionsIntent.ConfirmIfWorks -> updateState { it.copy(ifWorks = intent.ifWorks) }
            is TransactionsIntent.SelectScreen -> {
                updateState { it.copy(chosenScreen = intent.screen) }
                loadTitlesDrawablesTexts()
            }
            is TransactionsIntent.SetTitlesDrawablesTexts -> updateState {
                it.copy(
                    titles = intent.titles,
                    drawables = intent.drawables,
                    texts = intent.texts
                )
            }
            is TransactionsIntent.SetProfiles -> updateState { it.copy(profiles = intent.profiles) }
        }
    }

    private fun updateState(update: (TransactionsState) -> TransactionsState) {
        _state.update(update)
    }

    fun getIconForSelectedMethodOrPerson(): Int {
        return when (_state.value.selectedMethodOrPerson) {
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

    private fun loadTitlesDrawablesTexts() {
        val (titles, drawables, texts) = when (_state.value.chosenScreen) {
            "Transactions" -> Triple(
                listOf("E-Payment", "Credit Card"),
                listOf(
                    listOf(
                        R.drawable.ic_paypal, R.drawable.ic_google_pay,
                        R.drawable.ic_trustly, R.drawable.ic_other_payment
                    ),
                    listOf(R.drawable.ic_mastercard, R.drawable.ic_unionpay)
                ),
                listOf(
                    listOf("Paypal", "Google Pay", "Trustly", "Other E-Payment"),
                    listOf("Mastercard", "Union Pay")
                )
            )
            "Transfer" -> {
                val userList = fetchUsers()
                Triple(
                    listOf("Add new recipient", "Transfer list"),
                    listOf(
                        listOf(R.drawable.ic_bank, R.drawable.ic_bank),
                        listOf(R.drawable.pictureprofile2, R.drawable.pictureprofile3)
                    ),
                    listOf(
                        listOf("Add New Bank Account", "Add New Credit Card"),
                        userList
                    )
                )
            }
            "Withdraw" -> Triple(emptyList(), emptyList(), emptyList()) // TODO: Add actual logic
            else -> Triple(emptyList(), emptyList(), emptyList())
        }

        if (titles.isNotEmpty() && drawables.isNotEmpty() && texts.isNotEmpty()) {
            handleIntent(TransactionsIntent.SetTitlesDrawablesTexts(titles, drawables, texts))
        }
    }


    private fun fetchUsers(): List<String>{
        val listOfNames = mutableListOf<String>()

        viewModelScope.launch {
            transactionsUseCase.fetchUsersProfiles {
                it.forEach { profile -> listOfNames.add(profile.name) }
            }
        }
        return listOfNames
    }

    fun fetchUserIDByName(name: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            transactionsUseCase.fetchUserIDByName(name, onResult)
        }
    }

    fun handleContinueButtonClick(name: String = "") {
        when (_state.value.chosenScreen) {
            "Transactions" -> updateAccountBalance()
            "Transfer" -> transferMoney(name)
        }
    }

    private fun updateAccountBalance() {
        val amount = _state.value.chosenAmount ?: 0f
        viewModelScope.launch {
            transactionsUseCase.updateUserAccount(amount.toDouble()) { success ->
                handleIntent(TransactionsIntent.ConfirmIfWorks(success))
            }
        }
    }

    private fun transferMoney(name: String) {
        fetchUserIDByName(name) { toUserID ->
            if (toUserID != null) {
                val amount = _state.value.chosenAmount ?: 0f
                viewModelScope.launch {
                    transactionsUseCase.transferMoney(toUserID, amount.toDouble()) { success ->
                        handleIntent(TransactionsIntent.ConfirmIfWorks(success))
                    }
                }
            } else {
                handleIntent(TransactionsIntent.ConfirmIfWorks(false))
            }
        }
    }
}