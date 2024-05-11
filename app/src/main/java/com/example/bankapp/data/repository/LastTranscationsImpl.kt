package com.example.bankapp.data.repository

import com.example.bankapp.data.model.firebase.LastTransactionsFireStore
import com.example.bankapp.data.model.firebase.UserFireStore
import com.example.bankapp.data.model.mappers.mapUserFireStoreToUserRealm
import com.example.bankapp.data.model.realm.LastTransactionsRealm
import com.example.bankapp.data.model.realm.UserRealm
import com.example.bankapp.di.MyApp
import io.realm.kotlin.ext.query

class LastTranscationsImpl : LastTranscationsRepo<LastTransactionsRealm>() {
    private val realm = MyApp.realm

    override fun add(item: LastTransactionsRealm) {
        realm.writeBlocking {
            copyToRealm(item)
        }
    }


    override fun update(item: LastTransactionsRealm, userId: String) {
        //TODO
    }

    override fun delete(item: LastTransactionsRealm) {
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

    override fun getLoggedUser(userId: String): UserRealm? {
        return realm.query<UserRealm>("userId == $0", userId).find().firstOrNull()
    }

    override fun getAllUsers(userId: String): List<LastTransactionsRealm> {
        TODO("Not yet implemented")
    }

    fun replaceTransactions(user: UserFireStore) {
        realm.writeBlocking {
            deleteAll()
            copyToRealm(mapUserFireStoreToUserRealm(user))
        }
    }
}