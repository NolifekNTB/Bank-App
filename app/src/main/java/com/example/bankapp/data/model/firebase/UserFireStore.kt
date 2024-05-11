package com.example.bankapp.data.model.firebase

import com.google.firebase.firestore.PropertyName

data class UserFireStore(
    @PropertyName("userId") var userId: String = "",
    @PropertyName("name") var name: String = "",
    @PropertyName("email") var email: String = "",
    @PropertyName("phone") var phone: String = "",
    @PropertyName("profilePicUrl") var profilePicUrl: String = "",
    @PropertyName("balance") var balance: Double = 0.0,
    @PropertyName("lastTransactions") var lastTransactions: List<LastTransactionsFireStore> = emptyList(),
    //@PropertyName("friendsIDs") var friendsIDs: List<String> = emptyList() //TODO
)