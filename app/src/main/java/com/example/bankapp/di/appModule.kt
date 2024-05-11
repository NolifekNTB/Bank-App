package com.example.bankapp.di

import com.example.bankapp.data.repository.FirebaseRepository
import com.example.bankapp.data.repository.LastTranscationsImpl
import com.example.bankapp.presentation.home.HomeViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val appModule = module {
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

    single {
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = firestoreSettings {
                isPersistenceEnabled = false
            }
        }
    }
}