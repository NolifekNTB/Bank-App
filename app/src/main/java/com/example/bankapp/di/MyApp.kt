package com.example.bankapp.di

import android.app.Application
import com.example.bankapp.data.model.realm.LastTransactionsRealm
import com.example.bankapp.data.model.realm.UserRealm
import com.example.bankapp.data.repository.LastTranscationsImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.LocalCacheSettings
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp: Application() {
    private val firebaseFirestore by lazy {
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = firestoreSettings {
                setLocalCacheSettings(persistentCacheSettings{})
                setLocalCacheSettings(memoryCacheSettings{})
            }
        }
    }
    companion object {
        lateinit var realm: Realm
    }


    override fun onCreate() {
        super.onCreate()
        initializeRealm()
        initializeKoin()

    }

    private fun initializeRealm() {
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(UserRealm::class, LastTransactionsRealm::class)
            )
        )
    }

    private fun initializeKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModule)
        }
    }

}
