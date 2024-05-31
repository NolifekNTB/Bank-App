package com.example.bankapp.home.di

import android.text.Editable.Factory
import com.example.bankapp.home.presentation.screens.topUp.TopUpUseCase
import com.example.bankapp.home.presentation.screens.topUp.TopUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val topUpModule = module {
    viewModel {
        TopUpViewModel(
            get()
        )
    }

    factory {
        TopUpUseCase(
            get()
        )
    }
}