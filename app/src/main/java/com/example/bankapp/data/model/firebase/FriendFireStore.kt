package com.example.bankapp.data.model.firebase

import com.google.firebase.firestore.PropertyName

data class FriendFireStore(
    @PropertyName("name") var name: String = "",
    @PropertyName("email") var email: String = "",
    @PropertyName("phone") var phone: String = "",
    @PropertyName("profilePicUrl") var profilePicUrl: String = "",
)