package com.example.bankapp.data.model

import com.google.firebase.firestore.PropertyName

data class User(
    @PropertyName("userId") var userId: String = "",
    @PropertyName("name") var name: String = "",
    @PropertyName("email") var email: String = "",
    @PropertyName("phone") var phone: String = "",
    @PropertyName("profilePicUrl") var profilePicUrl: String = "",
    @PropertyName("balance") var balance: Double = 0.0,
    @PropertyName("lastTransactions") var lastTransactions: List<LastTransactions> = emptyList(),
    //@PropertyName("friendsIDs") var friendsIDs: List<String> = emptyList() //TODO
)