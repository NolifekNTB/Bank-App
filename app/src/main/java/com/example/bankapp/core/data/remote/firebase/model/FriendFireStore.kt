package com.example.bankapp.core.data.remote.firebase.model

import com.google.firebase.firestore.PropertyName

data class FriendFireStore(
    @PropertyName("name") var name: String = "",
    @PropertyName("email") var email: String = "",
    @PropertyName("phone") var phone: String = "",
    @PropertyName("profilePicUrl") var profilePicUrl: String = "",
)