package com.example.bankapp.core.domain.mappers

import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore
import com.example.bankapp.core.data.local.realm.model.UserRealm

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
