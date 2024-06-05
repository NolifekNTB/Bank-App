package com.example.bankapp.home.di

import com.example.bankapp.home.domain.TransactionsUseCase
import com.example.bankapp.home.presentation.Transactions.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionsModule = module {
    viewModel {
        TransactionsViewModel(
            get()
        )
    }

    factory {
        TransactionsUseCase(
            get()
        )
    }
}