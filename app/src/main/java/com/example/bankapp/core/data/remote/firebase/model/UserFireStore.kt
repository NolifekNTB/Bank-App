package com.example.bankapp.core.data.remote.firebase.model

import com.google.firebase.firestore.PropertyName

data class UserFireStore(
    @PropertyName("userId") var userId: String = "",
    @PropertyName("name") var name: String = "",
    @PropertyName("email") var email: String = "",
    @PropertyName("phone") var phone: String = "",
    @PropertyName("profilePicUrl") var profilePicUrl: String = "",
    @PropertyName("balance") var balance: Double = 0.0,
    @PropertyName("lastTransactions") var lastTransactions: List<LastTransactionsFireStore> = emptyList(),
)