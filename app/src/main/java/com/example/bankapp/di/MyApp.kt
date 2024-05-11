package com.example.bankapp.di

import android.app.Application
import com.example.bankapp.data.model.realm.LastTransactionsRealm
import com.example.bankapp.data.model.realm.UserRealm
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp: Application() {
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
