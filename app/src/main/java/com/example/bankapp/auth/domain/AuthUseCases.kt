package com.example.bankapp.auth.domain

import com.example.bankapp.auth.data.repository.FirebaseUserRepositoryImpl
import com.example.bankapp.core.data.remote.firebase.model.LastTransactionsFireStore
import com.example.bankapp.core.data.remote.firebase.model.UserFireStore
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthUseCases(
    private val firebaseAuth: FirebaseAuth,
    private val repoFirebase: FirebaseUserRepositoryImpl
) {
    suspend fun createUserProfile(): Result<String> {
        val userId = firebaseAuth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))
        val user = UserFireStore(
            userId = userId,
            name = "John Doe",
            email = "gXyjz@example.com",
            phone = "123456789",
            profilePicUrl = "https://cdn.dribbble.com/users/17793/screenshots/16101765/media/beca221aaebf1d3ea7684ce067bc16e5.png",
            balance = 0.0,
            lastTransactions = listOf(
                LastTransactionsFireStore(
                    name = "Anna Nowak",
                    price = 100.0,
                    timeOrPhoneNumber = "123456789",
                    iconLogo = "https://example.com/anna.png"
                )
            )
        )

        return try {
            repoFirebase.createUserProfile(user, {}, {})
            Result.success("User profile created successfully.")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<String> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success("User logged in successfully.")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerUser(email: String, password: String): Result<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success("User registered successfully.")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}