package com.example.bankapp.core.presentation.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bankapp.home.presentation.HomeScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpViewModel
import com.google.firebase.auth.FirebaseAuth

private object RoutesHome {
    const val HOME_GRAPH = "HomeGraph"
    const val HOME = "home"
}

fun NavGraphBuilder.homeNavGraph(
    auth: FirebaseAuth,
    navController: NavHostController,
) {
    navigation(
        route = RoutesHome.HOME_GRAPH,
        startDestination = RoutesHome.HOME
    ){
        composable(route = RoutesHome.HOME) {
            HomeScreen(auth = auth, onNavigation = { route ->
                navController.navigate(route)
            })
        }

        topUpNavGraph(navController = navController)

        transferNavGraph(navController = navController)
    }
}



