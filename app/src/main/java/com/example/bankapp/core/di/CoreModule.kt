package com.example.bankapp.core.di

import com.example.bankapp.auth.data.repository.FirebaseUserRepositoryImpl
import com.example.bankapp.home.data.repository.LastTransactionsImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import org.koin.dsl.module

val coreModule = module {
    single {
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = firestoreSettings {
                isPersistenceEnabled = false
            }
        }
    }

    single { FirebaseAuth.getInstance() }

    single {
        LastTransactionsImpl()
    }

    single {
        FirebaseUserRepositoryImpl(get())
    }
}