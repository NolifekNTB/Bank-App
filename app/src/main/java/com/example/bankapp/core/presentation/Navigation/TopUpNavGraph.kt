package com.example.bankapp.core.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bankapp.home.presentation.screens.topUp.SecondTopUpScreen
import com.example.bankapp.home.presentation.screens.topUp.ThirdTopUpScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpSuccessScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpViewModel
import org.koin.androidx.compose.getViewModel

private object RoutesTopUp {
    const val TOP_UP_GRAPH = "TopUpGraph"
    const val TOP_UP = "TopUp"
    const val TOP_UP2 = "topUp2"
    const val TOP_UP3 = "topUp3"
    const val TOP_UP4 = "topUp4"
}

fun NavGraphBuilder.topUpNavGraph(
    navController: NavHostController
) {
    navigation(
        route = RoutesTopUp.TOP_UP_GRAPH,
        startDestination = RoutesTopUp.TOP_UP
    ) {
        composable(route = RoutesTopUp.TOP_UP) { entry ->
            val topUpViewModel = entry.sharedViewModelSearch<TopUpViewModel>(navController, RoutesTopUp.TOP_UP_GRAPH)

            TopUpScreen(topUpViewModel = topUpViewModel) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTopUp.TOP_UP2)
            }
        }

        composable(route = RoutesTopUp.TOP_UP2) { entry ->
            val topUpViewModel = entry.sharedViewModelSearch<TopUpViewModel>(navController, RoutesTopUp.TOP_UP_GRAPH)

            SecondTopUpScreen(topUpViewModel = topUpViewModel) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTopUp.TOP_UP3)
            }
        }

        composable(route = RoutesTopUp.TOP_UP3) { entry ->
            val topUpViewModel = entry.sharedViewModelSearch<TopUpViewModel>(navController, RoutesTopUp.TOP_UP_GRAPH)

            ThirdTopUpScreen(topUpViewModel = topUpViewModel) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTopUp.TOP_UP4)
            }
        }

        composable(route = RoutesTopUp.TOP_UP4) { entry ->
            val topUpViewModel = entry.sharedViewModelSearch<TopUpViewModel>(navController, RoutesTopUp.TOP_UP_GRAPH)

            TopUpSuccessScreen(topUpViewModel = topUpViewModel)
            { backOrGo ->
                if (backOrGo == "backe" +
                    "") navController.navigate("HomeGraph")
                else navController.popBackStack()
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModelSearch(
    navController: NavHostController,
    navGraphRoute: String
): T {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return parentEntry.getKoinViewModel()
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.getKoinViewModel(): T {
    return getViewModel(owner = this)
}