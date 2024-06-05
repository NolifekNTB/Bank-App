package com.example.bankapp.home.presentation.Transactions.mvi

import com.example.bankapp.core.data.remote.firebase.model.FriendFireStore

data class TransactionsState(
    val selectedMethodOrPerson: String? = null,
    val chosenAmount: Float? = null,
    val ifWorks: Boolean? = null,
    val chosenScreen: String? = null,
    val titles: List<String> = emptyList(),
    val drawables: List<List<Int>> = emptyList(),
    val texts: List<List<String>> = emptyList(),
    val profiles: List<FriendFireStore> = emptyList()
)