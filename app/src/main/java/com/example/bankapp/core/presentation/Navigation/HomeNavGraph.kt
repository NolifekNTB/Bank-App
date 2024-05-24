package com.example.bankapp.core.presentation.Navigation

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
import com.google.firebase.auth.FirebaseAuth

private object Routes {
    const val HOME_GRAPH = "HomeGraph"
    const val HOME = "home"
    const val TOP_UP = "Top Up"
    const val TOP_UP2 = "topUp2/{selectedMethod}"
    const val TOP_UP3 = "topUp3/{selectedMethod}/{chosenAmount}"
    const val TOP_UP4 = "topUp4/{selectedMethod}/{chosenAmount}/{ifWorks}"

    const val ARG_SELECTED_METHOD = "selectedMethod"
    const val ARG_CHOSEN_AMOUNT = "chosenAmount"
    const val ARG_IF_WORKS = "ifWorks"
}

fun NavGraphBuilder.homeNavGraph(auth: FirebaseAuth, navController: NavHostController) {
    navigation(
        route = Routes.HOME_GRAPH,
        startDestination = Routes.HOME
    ){
        composable(route = Routes.HOME) {
            HomeScreen(auth = auth, onNavigation = { route -> navController.navigate(route) })
        }

        composable(route = Routes.TOP_UP) {
            TopUpScreen(){ selectedMethod ->
                if(selectedMethod == "back") navController.popBackStack()
                else navController.navigate("topUp2/$selectedMethod")
            }
        }

        composable(
            route = Routes.TOP_UP2,
            arguments = listOf(navArgument(Routes.ARG_SELECTED_METHOD) { type = NavType.StringType })
        ) { backStackEntry ->
            val selectedMethod = backStackEntry.arguments?.getString(Routes.ARG_SELECTED_METHOD) ?: ""

            SecondTopUpScreen(selectedMethod = selectedMethod){ amount ->
                if(amount < 0) navController.popBackStack()
                else navController.navigate("topUp3/$selectedMethod/$amount")
                }
            }

        composable(
            route = Routes.TOP_UP3,
            arguments = listOf(
                navArgument(Routes.ARG_SELECTED_METHOD) { type = NavType.StringType },
                navArgument(Routes.ARG_CHOSEN_AMOUNT) { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val selectedMethod = backStackEntry.arguments?.getString(Routes.ARG_SELECTED_METHOD) ?: ""
            val chosenAmount = backStackEntry.arguments?.getFloat(Routes.ARG_CHOSEN_AMOUNT) ?: 0.0f

            ThirdTopUpScreen(selectedMethod = selectedMethod, chosenAmount = chosenAmount){ route, ifWorks ->
                if(route == "topUp4")
                    navController.navigate("topUp4/$selectedMethod/$chosenAmount/$ifWorks")
                else
                    navController.popBackStack()

            }
        }

        composable(
            route = Routes.TOP_UP4,
            arguments = listOf(
                navArgument(Routes.ARG_SELECTED_METHOD) { type = NavType.StringType },
                navArgument(Routes.ARG_CHOSEN_AMOUNT) { type = NavType.FloatType },
                navArgument(Routes.ARG_IF_WORKS) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val selectedMethod = backStackEntry.arguments?.getString(Routes.ARG_SELECTED_METHOD) ?: ""
            val chosenAmount = backStackEntry.arguments?.getFloat(Routes.ARG_CHOSEN_AMOUNT) ?: 0.0f
            val ifWorks = backStackEntry.arguments?.getString(Routes.ARG_IF_WORKS) ?: ""

            TopUpSuccessScreen(selectedMethod = selectedMethod, chosenAmount = chosenAmount, ifWorks = ifWorks)
            { route ->
                if(route == "back") navController.navigate(Routes.HOME)
                else navController.popBackStack()
            }
        }
    }
}



