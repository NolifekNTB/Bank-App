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
import com.example.bankapp.auth.presentation.mvi.AuthViewState
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.inject


@Composable
fun AppNavigation() {
    val authViewModel: AuthViewModel = getViewModel()
    val navController = rememberNavController()
    val viewState by authViewModel.viewState.collectAsState()
    val auth by inject<FirebaseAuth>()

    NavHost(
        navController = navController,
        startDestination = if (viewState is AuthViewState.Success) "main" else "login"
    ) {
        composable("login") {
            LoginScreen(navController, authViewModel)
        }

        composable("main") {
            HomeScreen(auth){
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }
    }
}