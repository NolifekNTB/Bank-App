package com.example.bankapp.presentation.IntentAndStates

import com.example.bankapp.data.model.firebase.FriendFireStore
import com.example.bankapp.data.model.realm.UserRealm

sealed class ViewState {
    data object Loading : ViewState()
    data class DataLoaded(
        val user: UserRealm? = null,
        val allUsers: List<FriendFireStore> = emptyList()
    ) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
