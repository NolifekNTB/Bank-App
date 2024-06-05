package com.example.bankapp.home.presentation.home.mvi

import com.example.bankapp.core.data.local.realm.model.FriendRealm
import com.example.bankapp.core.data.local.realm.model.UserRealm

sealed class ViewState {
    data object Loading : ViewState()
    data class DataLoaded(
        val user: UserRealm? = null,
        val allUsers: List<FriendRealm> = emptyList()
    ) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
