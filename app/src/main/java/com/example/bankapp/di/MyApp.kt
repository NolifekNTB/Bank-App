package com.example.bankapp.di

import android.app.Application
import com.example.bankapp.data.model.LastTranscations
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MyApp: Application() {

    companion object {
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    LastTranscations::class
                )
            )
        )
    }
}