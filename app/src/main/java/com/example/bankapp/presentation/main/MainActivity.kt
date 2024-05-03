package com.example.bankapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bankapp.presentation.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                HomeScreen()
            } else {
                AppNavigation(auth = auth)
            }
        }
    }
}

//user1@gmail.com
//user#1

//user2@gmail.com
//user#2
