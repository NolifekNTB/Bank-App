package com.example.bankapp.home.presentation.screens.topUp.mvi

sealed class TopUpIntent {
    data class SelectMethod(val method: String) : TopUpIntent()
    data class ChooseAmount(val amount: Float) : TopUpIntent()
    data class ConfirmIfWorks(val ifWorks: Boolean) : TopUpIntent()
    data class SelectScreen(val screen: String) : TopUpIntent()
    data class titlesDrawablesTexts(
        val titles: List<String>,
        val drawables: List<List<Int>>,
        val texts: List<List<String>>,
    ) : TopUpIntent()
}