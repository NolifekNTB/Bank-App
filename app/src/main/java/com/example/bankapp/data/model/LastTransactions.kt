package com.example.bankapp.data.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

data class LastTransactions(
    var name: String = "",
    var price: Double = 0.0,
    var timeOrPhoneNumber: String = "",
    var iconLogo: String = ""
)

/*class LastTransactions: RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var userId: String = ""
    var transactions: RealmList<Transactions> = realmListOf()
}

class Transactions: RealmObject {
    var name: String = ""
    var price: Double = 0.0
    var timeOrPhoneNumber: String = ""
}

 */