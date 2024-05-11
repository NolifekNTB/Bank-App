package com.example.bankapp.data.model.mappers

import com.example.bankapp.data.model.firebase.LastTransactionsFireStore
import com.example.bankapp.data.model.firebase.UserFireStore
import com.example.bankapp.data.model.realm.UserRealm

fun mapUserRealmToUserFireStore(userRealm: UserRealm): UserFireStore {
    val userFirestore = UserFireStore(
        userId = userRealm.userId,
        name = userRealm.name,
        email = userRealm.email,
        phone = userRealm.phone,
        profilePicUrl = userRealm.profilePicUrl,
        balance = userRealm.balance,
        lastTransactions = userRealm.lastTransactions.map {
            LastTransactionsFireStore(
                name = it.name,
                price = it.price,
                timeOrPhoneNumber = it.timeOrPhoneNumber,
                iconLogo = it.iconLogo
            )
        })
    return userFirestore
}
