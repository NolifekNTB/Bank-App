package com.example.bankapp.core.domain.mappers

import com.example.bankapp.core.data.remote.firebase.model.UserFireStore
import com.example.bankapp.core.data.local.realm.LastTransactionsRealm
import com.example.bankapp.core.data.local.realm.UserRealm
import io.realm.kotlin.ext.realmListOf

/*fun mapUserFireStoreToUserRealm(userFireStore: UserFireStore): UserRealm {
    val userRealm = UserRealm().apply {
        userId = userFireStore.userId
        name = userFireStore.name
        email = userFireStore.email
        phone = userFireStore.phone
        profilePicUrl = userFireStore.profilePicUrl
        balance = userFireStore.balance

        lastTransactions = userFireStore.lastTransactions.map {
            LastTransactionsRealm().apply {
                name = it.name
                price = it.price
                timeOrPhoneNumber = it.timeOrPhoneNumber
                iconLogo = it.iconLogo
            }
        } as RealmList<LastTransactionsRealm>
    }

    return userRealm
}

 */

fun mapUserFireStoreToUserRealm(userFireStore: UserFireStore): UserRealm {
    val userRealm = UserRealm().apply {
        userId = userFireStore.userId
        name = userFireStore.name
        email = userFireStore.email
        phone = userFireStore.phone
        profilePicUrl = userFireStore.profilePicUrl
        balance = userFireStore.balance

        // Create a new RealmList
        val transactionsRealmList = realmListOf<LastTransactionsRealm>()

        // Map each item in userFireStore.lastTransactions to LastTransactionsRealm
        userFireStore.lastTransactions.forEach {
            val lastTransactionRealm = LastTransactionsRealm().apply {
                name = it.name
                price = it.price
                timeOrPhoneNumber = it.timeOrPhoneNumber
                iconLogo = it.iconLogo
            }
            transactionsRealmList.add(lastTransactionRealm)
        }

        // Assign the newly created RealmList
        lastTransactions = transactionsRealmList
    }

    return userRealm
}