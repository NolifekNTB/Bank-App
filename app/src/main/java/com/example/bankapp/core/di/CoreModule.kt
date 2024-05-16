package com.example.bankapp.core.di

import com.example.bankapp.auth.data.repository.FirebaseRepository
import com.example.bankapp.home.data.repository.LastTranscationsImpl
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
        LastTranscationsImpl()
    }

    single {
        FirebaseRepository(get())
    }
}