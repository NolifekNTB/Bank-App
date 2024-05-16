package com.example.bankapp.auth.di

import com.example.bankapp.auth.presentation.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel {
        AuthViewModel(get())
    }
}