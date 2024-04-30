package com.example.bankapp.domain.repository

import com.example.bankapp.data.model.LastTranscations
import com.example.bankapp.di.MyApp
import io.realm.kotlin.Realm

class LastTranscationsImpl : LastTranscationsRepo<LastTranscations>() {
    private val realm = MyApp.realm

    override fun add(item: LastTranscations) {
        realm.writeBlocking {
            copyToRealm(item)
        }
    }

    override fun update(item: LastTranscations) {
        realm.writeBlocking {
            findLatest(item)?.apply {
                name = item.name
                price = item.price
                timeOrPhoneNumber = item.timeOrPhoneNumber
            }
        }
    }

    override fun delete(item: LastTranscations) {
        realm.writeBlocking {
            val lastItem = findLatest(item) ?: return@writeBlocking
            delete(lastItem)
        }
    }

    override fun deleteAll() {
        realm.writeBlocking {
            deleteAll()
        }
    }

    override fun getAll(): List<LastTranscations> {
        return realm.query(LastTranscations::class).find()
    }
}