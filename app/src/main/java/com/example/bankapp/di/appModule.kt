package com.example.bankapp.di

import com.example.bankapp.data.repository.FirebaseRepository
import com.example.bankapp.data.repository.LastTranscationsImpl
import com.example.bankapp.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val appModule = module {
    // Define a ViewModel - Be sure to import koin-androidx-viewmodel dependency
    viewModel {
        HomeViewModel(
            get(),
            get()
        )
    }

    single {
        LastTranscationsImpl()
    }

    single {
        FirebaseRepository(get())
    }
}