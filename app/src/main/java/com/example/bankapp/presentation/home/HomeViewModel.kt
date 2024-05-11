package com.example.bankapp.presentation.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.data.model.firebase.LastTransactionsFireStore
import com.example.bankapp.data.model.firebase.UserFireStore
import com.example.bankapp.data.model.mappers.mapUserFireStoreToUserRealm
import com.example.bankapp.data.repository.FirebaseRepository
import com.example.bankapp.data.repository.LastTranscationsImpl
import com.example.bankapp.presentation.IntentAndStates.ViewIntent
import com.example.bankapp.presentation.IntentAndStates.ViewState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    val repoRealm: LastTranscationsImpl,
    val repoFirebase: FirebaseRepository
): ViewModel() {
    private val _state = MutableStateFlow<ViewState>(ViewState.Loading)
    val state: StateFlow<ViewState> = _state.asStateFlow()

    fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.LoadData -> loadData(intent.userID)
            is ViewIntent.addTransaction -> addNewTransaction(intent.transaction)
            /*is ViewIntent.LoadTransactions -> loadTransactions(userID)
            is ViewIntent.LoadUserData -> loadUserData(userID)
            is ViewIntent.LoadAllUsers -> loadAllUserProfiles()
            is ViewIntent.AddTransaction -> addTransaction(intent.transaction)
            is ViewIntent.UpdateTransaction -> updateTransaction(intent.transaction, intent.userId)

             */
        }
    }

    private fun loadData(userId: String) {
        viewModelScope.launch {

            val cachedUserData = repoRealm.getLoggedUser(userId)

            if (cachedUserData != null && cachedUserData.userId.isNotEmpty()) {
                _state.value = ViewState.DataLoaded(cachedUserData, emptyList()) //TODO: allUsers
            }

            try {
                val userDeferred = async { repoFirebase.fetchUserData(userId) }
                val allUsers = async { repoFirebase.fetchUserProfiles() }

                val user = userDeferred.await()
                val users = allUsers.await()

                if (user != null) {
                    repoRealm.replaceTransactions(user)
                    _state.value = ViewState.DataLoaded(
                        mapUserFireStoreToUserRealm(user),
                        users
                    )
                }

            } catch (e: Exception) {
                _state.value = ViewState.Error(e)
                Log.d("testowanie", "Error loading user data", e)
            }
        }
    }


    fun createUserProfile(
        name: String, email: String, phone: String, profilePicUrl: String
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val transactions = listOf(
            LastTransactionsFireStore(
                name = "Anna Nowak",
                price = 100.0,
                timeOrPhoneNumber = "123456789",
                iconLogo = "https://example.com/anna.png"
            )
        )
        val user = UserFireStore(userId, name, email, phone, profilePicUrl, 0.0, transactions)

        repoFirebase.createUserProfile(user,
            onSuccess = { Log.d("testowanie", "User profile created successfully.") },
            onFailure = { e -> Log.d("testowanie", "Error creating user profile", e) }
        )
    }

    fun addNewTransaction(transaction: LastTransactionsFireStore) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        repoFirebase.addTransaction(transaction, userId)
    }
    /*private fun loadTransactions(userId: String = "") {
        viewModelScope.launch {
            try {
                val transactions = repoRealm.getAll(userId)
                _state.value = ViewState.DataLoaded(transactions = transactions)
                if(transactions != null) {

                }
            } catch (e: Exception) {
                _state.value = ViewState.Error(e)
            }
        }
    }

        private suspend fun loadUserData(userId: String) {
            val user = repoFirebase.fetchUserData(userId)

            if (user != null) {
                _state.value = ViewState.DataLoaded(user = user)
            } else {
                _state.value = ViewState.Error(Exception("User not found"))
            }
        }

    private fun addTransaction(transaction: LastTransactions) {
            try {
                repoRealm.add(transaction)
                loadTransactions()
            } catch (e: Exception) {
                _state.value = ViewState.Error(e)
        }
    }

    fun updateTransaction(transaction: LastTransactions, userId: String) {
        try {
            repoRealm.update(transaction, userId)
            loadTransactions()
        } catch (e: Exception) {
            _state.value = ViewState.Error(e)
        }
    }

    private fun loadAllUserProfiles() {
        viewModelScope.launch {
            try {
                val users = repoFirebase.fetchUserProfiles()
                if (users.isNotEmpty()) {
                    _state.value = ViewState.DataLoaded(allUsers = users)
                } else {
                    _state.value = ViewState.Error(Exception("No users found"))
                }
            } catch (e: Exception) {
                _state.value = ViewState.Error(e)
            }
        }
    }


     */

}