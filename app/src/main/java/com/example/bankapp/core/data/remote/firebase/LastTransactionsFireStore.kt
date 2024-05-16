package com.example.bankapp.core.data.remote.firebase

data class LastTransactionsFireStore(
    var name: String = "",
    var price: Double = 0.0,
    var timeOrPhoneNumber: String = "",
    var iconLogo: String = ""
)