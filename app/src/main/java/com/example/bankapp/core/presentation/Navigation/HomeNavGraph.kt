package com.example.bankapp.core.presentation.Navigation

import androidx.compose.runtime.collectAsState
import com.example.bankapp.home.presentation.screens.topUp.SecondTopUpScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.bankapp.home.presentation.HomeScreen
import com.example.bankapp.home.presentation.screens.topUp.ThirdTopUpScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpSuccessScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpViewModel
import com.example.bankapp.home.presentation.screens.topUp.mvi.TopUpIntent
import com.google.firebase.auth.FirebaseAuth

private object Routes {
    const val HOME_GRAPH = "HomeGraph"
    const val HOME = "home"
    const val TOP_UP = "TopUp"
    const val TOP_UP2 = "topUp2"
    const val TOP_UP3 = "topUp3"
    const val TOP_UP4 = "topUp4"
}

fun NavGraphBuilder.homeNavGraph(
    auth: FirebaseAuth,
    navController: NavHostController,
    topUpViewModel: TopUpViewModel
) {
    navigation(
        route = Routes.HOME_GRAPH,
        startDestination = Routes.HOME
    ){
        composable(route = Routes.HOME) {
            HomeScreen(auth = auth, onNavigation = { route -> navController.navigate(route) })
        }

        composable(route = Routes.TOP_UP) {
            TopUpScreen(topUpViewModel = topUpViewModel){ backOrGo ->
                if(backOrGo == "back") navController.popBackStack()
                else navController.navigate(Routes.TOP_UP2)
            }
        }

        composable(route = Routes.TOP_UP2) {
            SecondTopUpScreen(topUpViewModel = topUpViewModel) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(Routes.TOP_UP3)
            }
        }

        composable(route = Routes.TOP_UP3) {
            ThirdTopUpScreen(topUpViewModel = topUpViewModel){ backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(Routes.TOP_UP4)
            }
            }
        }

        composable(route = Routes.TOP_UP4) {
            TopUpSuccessScreen(topUpViewModel =topUpViewModel)
            { backOrGo ->
                if(backOrGo == "back") navController.navigate(Routes.HOME)
                else navController.popBackStack()
            }
        }
    }



