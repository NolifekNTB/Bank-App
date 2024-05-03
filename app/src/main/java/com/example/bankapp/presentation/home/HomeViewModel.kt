package com.example.bankapp.presentation.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.data.model.LastTranscations
import com.example.bankapp.data.model.User
import com.example.bankapp.data.repository.LastTranscationsImpl
import com.example.bankapp.presentation.Intent.ViewIntent
import com.example.bankapp.presentation.Intent.ViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel: ViewModel() {
    val repo = LastTranscationsImpl()

    private val _state = MutableStateFlow<ViewState>(ViewState.Loading)
    val state: StateFlow<ViewState> = _state.asStateFlow()

    init {
        processIntent(ViewIntent.LoadTransactions)
    }

    fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.LoadTransactions -> loadTransactions()
            is ViewIntent.AddTransaction -> addTransaction(intent.transaction)
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            try {
                val transactions = repo.getAll()
                _state.value = ViewState.Success(transactions)
            } catch (e: Exception) {
                _state.value = ViewState.Error(e)
            }
        }
    }

    private fun addTransaction(transaction: LastTranscations) {
        viewModelScope.launch {
            try {
                repo.add(transaction)
                loadTransactions()
            } catch (e: Exception) {
                _state.value = ViewState.Error(e)
            }
        }
    }

    fun createUserProfile(name: String, email: String, phone: String, profilePicUrl: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return  // Ensure the user is logged in
        val user = hashMapOf(
            "userId" to userId,
            "name" to name,
            "email" to email,
            "phone" to phone,
            "profilePicUrl" to profilePicUrl,
            "balance" to 0.0
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId).set(user)
            .addOnSuccessListener {
                Log.d("testowanie", "User profile created successfully.")
            }
            .addOnFailureListener { e ->
                Log.d("testowanie", "Error creating user profile", e)
            }
    }

    fun fetchUserData(userId: String, onResult: (User?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val user = User(
                        userId = document.getString("userId") ?: "",
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: "",
                        phone = document.getString("phone") ?: "",
                        profilePicUrl = document.getString("profilePicUrl") ?: "",
                        balance = document.getDouble("balance") ?: 0.0
                    )
                    onResult(user)
                } else {
                    onResult(null)  // No user found
                }
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    suspend fun fetchUserProfiles(): List<User> {
        val db = FirebaseFirestore.getInstance()
        return try {
            val snapshot = db.collection("users")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(User::class.java)?.copy(userId = doc.id)
            }
        } catch (e: Exception) {
            Log.e("testowanie", "Error fetching users: ${e.message}", e)
            emptyList()
        }
    }
}