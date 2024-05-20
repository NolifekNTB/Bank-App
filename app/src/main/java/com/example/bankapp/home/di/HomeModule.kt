package com.example.bankapp.home.di

import com.example.bankapp.home.domain.HomeUseCase
import com.example.bankapp.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            get(),
            get()
        )
    }

    factory {
        HomeUseCase(
            get(),
            get()
        )
    }
}