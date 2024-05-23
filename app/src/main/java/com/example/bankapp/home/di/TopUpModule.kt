package com.example.bankapp.home.di

import com.example.bankapp.home.presentation.HomeViewModel
import com.example.bankapp.home.presentation.screens.TopUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val topUpModule = module {
    viewModel {
        TopUpViewModel(
            get()
        )
    }
}