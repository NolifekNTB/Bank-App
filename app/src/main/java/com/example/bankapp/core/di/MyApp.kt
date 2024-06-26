package com.example.bankapp.core.di

import android.app.Application
import com.example.bankapp.auth.di.authModule
import com.example.bankapp.core.data.local.realm.model.FriendRealm
import com.example.bankapp.core.data.local.realm.model.LastTransactionsRealm
import com.example.bankapp.core.data.local.realm.model.UserRealm
import com.example.bankapp.home.di.homeModule
import com.example.bankapp.home.di.transactionsModule
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
                schema = setOf(
                    UserRealm::class,
                    LastTransactionsRealm::class,
                    FriendRealm::class
                )
            )
        )
    }

    private fun initializeKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(coreModule)
            modules(authModule)
            modules(homeModule)
            modules(transactionsModule)
        }
    }
}
