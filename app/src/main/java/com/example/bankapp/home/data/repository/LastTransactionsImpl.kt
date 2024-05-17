package com.example.bankapp.home.data.repository

import com.example.bankapp.core.data.local.realm.model.FriendRealm
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore
import com.example.bankapp.core.domain.mappers.mapUserFireStoreToUserRealm
import com.example.bankapp.core.data.local.realm.model.LastTransactionsRealm
import com.example.bankapp.core.data.local.realm.model.UserRealm
import com.example.bankapp.core.data.local.realm.repository.LastTransactionsRepo
import com.example.bankapp.core.data.remote.firebase.model.FriendFireStore
import com.example.bankapp.core.di.MyApp
import com.example.bankapp.core.domain.mappers.mapFriendFireStoreToFriendRealm
import io.realm.kotlin.ext.query

class LastTransactionsImpl : LastTransactionsRepo<LastTransactionsRealm> {
    private val realm = MyApp.realm

    override fun getLoggedUser(userId: String): UserRealm? {
        return try {
            realm.query<UserRealm>("userId == $0", userId).find().firstOrNull()
        } catch (e: Exception) {
            logError("Error fetching logged user", e)
            null
        }
    }


    override fun getAllUsers(): List<FriendRealm> {
        return try {
            realm.query<FriendRealm>().find()
        } catch (e: Exception) {
            logError("Error fetching all users", e)
            emptyList()
        }
    }

    override fun add(item: LastTransactionsRealm) {
        try {
            realm.writeBlocking {
                copyToRealm(item)
            }
            logInfo("Transaction added: $item")
        } catch (e: Exception) {
            logError("Error adding transaction", e)
        }
    }

    override fun update(item: LastTransactionsRealm, userId: String) {
        logInfo("update is not yet implemented.")
        //TODO
    }

    override fun delete(item: LastTransactionsRealm) {
        try {
            realm.writeBlocking {
                val lastItem = findLatest(item) ?: return@writeBlocking
                delete(lastItem)
            }
            logInfo("Transaction deleted: $item")
        } catch (e: Exception) {
            logError("Error deleting transaction", e)
        }
    }

    override fun deleteAllTransactions() {
        try {
            realm.writeBlocking {
                deleteAll()
            }
            logInfo("All transactions deleted.")
        } catch (e: Exception) {
            logError("Error deleting all transactions", e)
        }
    }

    override fun replaceTransactions(user: UserFireStore) {
        try {
            realm.writeBlocking {
                copyToRealm(mapUserFireStoreToUserRealm(user))
            }
            logInfo("Transactions replaced for user: ${user.userId}")
        } catch (e: Exception) {
            logError("Error replacing transactions", e)
        }
    }

    override fun replaceFriends(friendFireStore: List<FriendFireStore>) {
        try {
            realm.writeBlocking {
                friendFireStore.forEach { friend ->
                    val realmFriend = mapFriendFireStoreToFriendRealm(friend)
                    copyToRealm(realmFriend)
                }
            }
        } catch (e: Exception) {
            logError("Error replacing transactions", e)
        }
    }

    private fun logError(message: String, exception: Exception) {
        println("$message: ${exception.message}")
    }

    private fun logInfo(message: String) {
        println(message)
    }
}