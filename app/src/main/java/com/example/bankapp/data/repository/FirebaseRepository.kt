package com.example.bankapp.data.repository

import android.util.Log
import com.example.bankapp.data.model.LastTransactions
import com.example.bankapp.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()

    fun createUserProfile(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(user.userId).set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    suspend fun fetchUserData(userId: String): User? = suspendCoroutine { continuation ->
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)?.copy(userId = document.id)
                continuation.resume(user)
            }
            .addOnFailureListener { exception ->
                Log.e("testowanie", "Error fetching user data: ${exception.message}", exception)
                continuation.resume(null)
            }
    }

    suspend fun fetchUserProfiles(): List<User> {
        return try {
            val snapshot = db.collection("users").get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(User::class.java)?.copy(userId = doc.id)
            }
        } catch (e: Exception) {
            Log.e("testowanie", "Error fetching users: ${e.message}", e)
            emptyList()
        }
    }

    fun addTransaction(transaction: LastTransactions, userID: String) {
        db.collection("users").document(userID)
            .update(
                "lastTransactions",
                FieldValue.arrayUnion(transaction)
            )
    }

}