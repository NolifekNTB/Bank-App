package com.example.bankapp.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bankapp.home.presentation.HomeScreen
import com.example.bankapp.auth.presentation.AuthViewModel
import com.example.bankapp.auth.presentation.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.inject


@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val user by authViewModel.currentUser.collectAsState()
    val auth by inject<FirebaseAuth>()

    NavHost(
        navController = navController,
        startDestination = if (user != null) "main" else "login"
    ) {
        composable("login") {
            LoginScreen(auth, navController)
        }

        composable("main") {
            HomeScreen(auth, authViewModel){
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }
    }
}