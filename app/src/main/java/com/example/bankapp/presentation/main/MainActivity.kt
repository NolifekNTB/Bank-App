package com.example.bankapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bankapp.presentation.home.HomeScreen
import com.example.bankapp.presentation.login.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel = getViewModel()

        setContent {
            AppNavigation(authViewModel)
        }
    }
}

//user1@gmail.com
//user#1

//user2@gmail.com
//user#2

//user3@gmail.com
//user#3

