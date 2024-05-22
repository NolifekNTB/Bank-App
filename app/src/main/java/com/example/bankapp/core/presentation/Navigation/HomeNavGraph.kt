package com.example.bankapp.core.presentation.Navigation

import SecondTopUpScreen
import TopUpScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bankapp.home.presentation.HomeScreen
import com.example.bankapp.home.presentation.screens.ThirdTopUpScreen
import com.example.bankapp.home.presentation.screens.TopUpSuccessScreen
import com.google.firebase.auth.FirebaseAuth

fun NavGraphBuilder.homeNavGraph(auth: FirebaseAuth, navController: NavHostController) {
    navigation(
        route = "HomeGraph",
        startDestination = "home"
    ){
        composable(route = "home") {
            HomeScreen(
                auth = auth,
                onNavigation = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(route = "Top Up") {
            TopUpScreen(){ route ->
                if(route == "topUp2"){
                    navController.navigate("topUp2")
                } else {
                    navController.popBackStack()
                }
            }
        }

        composable(route = "topUp2") {
            SecondTopUpScreen(){ route ->
                if(route == "topUp3"){
                    navController.navigate("topUp3")
                } else {
                    navController.popBackStack()
                }
            }
        }
        composable(route = "topUp3") {
            ThirdTopUpScreen(){ route ->
                if(route == "topUp4"){
                    navController.navigate("topUp4")
                } else {
                    navController.popBackStack()
                }
            }
        }
        composable(route = "topUp4") {
            TopUpSuccessScreen()
        }
    }
}



