package com.example.bankapp.home.presentation.mvi

import com.example.bankapp.core.data.remote.firebase.FriendFireStore
import com.example.bankapp.core.data.local.realm.UserRealm

sealed class ViewState {
    data object Loading : ViewState()
    data class DataLoaded(
        val user: UserRealm? = null,
        val allUsers: List<FriendFireStore> = emptyList()
    ) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
