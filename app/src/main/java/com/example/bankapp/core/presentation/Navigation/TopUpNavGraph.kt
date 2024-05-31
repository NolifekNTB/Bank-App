package com.example.bankapp.core.presentation.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bankapp.home.presentation.screens.topUp.SecondTopUpScreen
import com.example.bankapp.home.presentation.screens.topUp.ThirdTopUpScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpSuccessScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpViewModel

private object RoutesTopUp {
    const val TOP_UP_GRAPH = "TopUpGraph"
    const val TOP_UP = "TopUp"
    const val TOP_UP2 = "topUp2"
    const val TOP_UP3 = "topUp3"
    const val TOP_UP4 = "topUp4"
}

fun NavGraphBuilder.topUpNavGraph(
    navController: NavHostController,
    topUpViewModel: TopUpViewModel
) {
    navigation(
        route = RoutesTopUp.TOP_UP_GRAPH,
        startDestination = RoutesTopUp.TOP_UP
    ) {
        composable(route = RoutesTopUp.TOP_UP) {
            TopUpScreen(topUpViewModel = topUpViewModel) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTopUp.TOP_UP2)
            }
        }

        composable(route = RoutesTopUp.TOP_UP2) {
            SecondTopUpScreen(topUpViewModel = topUpViewModel) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTopUp.TOP_UP3)
            }
        }

        composable(route = RoutesTopUp.TOP_UP3) {
            ThirdTopUpScreen(topUpViewModel = topUpViewModel) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTopUp.TOP_UP4)
            }
        }

        composable(route = RoutesTopUp.TOP_UP4) {
            TopUpSuccessScreen(topUpViewModel = topUpViewModel)
            { backOrGo ->
                if (backOrGo == "back") navController.navigate("HomeGraph")
                else navController.popBackStack()
            }
        }
    }
}