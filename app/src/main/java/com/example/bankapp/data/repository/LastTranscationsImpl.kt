package com.example.bankapp.data.repository

import com.example.bankapp.data.model.LastTransactions
import io.realm.kotlin.ext.query

/*
class LastTranscationsImpl : LastTranscationsRepo<LastTransactions>() {
    private val realm = MyApp.realm

    override fun add(item: LastTransactions) {
        realm.writeBlocking {
            copyToRealm(item)
        }
    }


    override fun update(item: LastTransactions, userId: String) {
        realm.writeBlocking {
            val realmList = query<LastTransactions>("name = $0", userId).first().find()!!.transactions
            realmList.add(Transactions().apply {
                name = "Big Pond"
                price = 50.0
                timeOrPhoneNumber = "test"
            })
            //realmList.set(0, Transactions().apply { name = "Big Pond" })
        }
    }

    override fun delete(item: LastTransactions) {
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

    override fun getAll(userId: String): List<LastTransactions> {
        return realm.query<LastTransactions>("userId == $0", userId).find()
    }
}

 */