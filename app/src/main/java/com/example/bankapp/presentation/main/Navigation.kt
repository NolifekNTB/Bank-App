package com.example.bankapp.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bankapp.presentation.home.HomeScreen
import com.example.bankapp.presentation.login.LoginScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavigation(auth: FirebaseAuth) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(auth, navController)
        }
        composable("main") {
            HomeScreen()
        }
    }
}