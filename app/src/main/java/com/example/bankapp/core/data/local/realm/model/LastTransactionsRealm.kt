package com.example.bankapp.core.data.local.realm.model

import io.realm.kotlin.types.RealmObject

class LastTransactionsRealm: RealmObject {
    var name: String = ""
    var price: Double = 0.0
    var timeOrPhoneNumber: String = ""
    var iconLogo: String = ""
}