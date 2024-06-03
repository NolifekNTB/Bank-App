package com.example.bankapp.home.presentation.screens.topUp.mvi

data class TopUpState(
    val selectedMethodOrPerson: String? = null,
    val chosenAmount: Float? = null,
    val ifWorks: Boolean? = null,
    val chosenScreen: String? = null,
    val titles: List<String> = emptyList(),
    val drawables: List<List<Int>> = emptyList(),
    val texts: List<List<String>> = emptyList()
)