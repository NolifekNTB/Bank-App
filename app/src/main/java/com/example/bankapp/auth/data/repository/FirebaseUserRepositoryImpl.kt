package com.example.bankapp.auth.data.repository

import android.util.Log
import com.example.bankapp.core.data.remote.firebase.model.FriendFireStore
import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore
import com.example.bankapp.core.data.remote.firebase.repository.FirebaseUserRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseUserRepositoryImpl(private val db: FirebaseFirestore): FirebaseUserRepository {
    override suspend fun createUserProfile(user: UserFireStore, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(user.userId).set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    override suspend fun fetchUser(userId: String): UserFireStore? = suspendCoroutine { continuation ->
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(UserFireStore::class.java)?.copy(userId = document.id)
                continuation.resume(user)
            }
            .addOnFailureListener { exception ->
                Log.e("testowanie", "Error fetching user data: ${exception.message}", exception)
                continuation.resume(null)
            }
    }

    override suspend fun fetchProfiles(): List<FriendFireStore> {
        return try {
            val snapshot = db.collection("users").get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(FriendFireStore::class.java)?.copy()
            }
        } catch (e: Exception) {
            Log.e("testowanie", "Error fetching users: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun addTransaction(transaction: LastTransactionsFireStore, userID: String) {
        db.collection("users").document(userID)
            .update(
                "lastTransactions",
                FieldValue.arrayUnion(transaction)
            )
    }

    override suspend fun changeAccountBalance(userID: String, amount: Double) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userID)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val currentBalance = snapshot.getDouble("balance") ?: 0.0
            val newBalance = currentBalance + amount
            transaction.update(userRef, "balance", newBalance)
        }.await()
    }
}