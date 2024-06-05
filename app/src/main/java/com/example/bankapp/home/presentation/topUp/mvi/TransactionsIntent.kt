package com.example.bankapp.home.presentation.Transactions.mvi

import com.example.bankapp.core.data.remote.firebase.model.FriendFireStore

sealed class TransactionsIntent {
    data class SelectMethod(val method: String) : TransactionsIntent()
    data class ChooseAmount(val amount: Float) : TransactionsIntent()
    data class ConfirmIfWorks(val ifWorks: Boolean) : TransactionsIntent()
    data class SelectScreen(val screen: String) : TransactionsIntent()
    data class SetTitlesDrawablesTexts(
        val titles: List<String>,
        val drawables: List<List<Int>>,
        val texts: List<List<String>>,
    ) : TransactionsIntent()
    data class SetProfiles(val profiles: List<FriendFireStore>) : TransactionsIntent()
}