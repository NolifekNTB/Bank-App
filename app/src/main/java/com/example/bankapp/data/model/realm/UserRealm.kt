package com.example.bankapp.data.model.realm

import com.example.bankapp.data.model.firebase.LastTransactionsFireStore
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class UserRealm: RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var userId: String = ""
    var name: String = ""
    var email: String = ""
    var phone: String = ""
    var profilePicUrl: String = ""
    var balance: Double = 0.0
    var lastTransactions: RealmList<LastTransactionsRealm> = realmListOf()
}