package com.example.bankapp.home.presentation.screens.topUp.mvi

data class TopUpState(
    val selectedMethod: String? = null,
    val chosenAmount: Float? = null,
    val ifWorks: Boolean? = null
)