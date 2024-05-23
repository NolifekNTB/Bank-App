package com.example.bankapp.core.presentation.Navigation

import SecondTopUpScreen
import TopUpScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            TopUpScreen(){ selectedMethod ->
                if(selectedMethod == "back") navController.popBackStack()
                else navController.navigate("topUp2/$selectedMethod")
            }
        }

        composable(
            route = "topUp2/{selectedMethod}",
            arguments = listOf(navArgument("selectedMethod") { type = NavType.StringType })
        ) { backStackEntry ->
            val selectedMethod = backStackEntry.arguments?.getString("selectedMethod") ?: ""

            SecondTopUpScreen(selectedMethod = selectedMethod){ amount ->
                if(amount < 0) navController.popBackStack()
                else navController.navigate("topUp3/$selectedMethod/$amount")
                }
            }

        composable(
            route = "topUp3/{selectedMethod}/{chosenAmount}",
            arguments = listOf(
                navArgument("selectedMethod") { type = NavType.StringType },
                navArgument("chosenAmount") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val selectedMethod = backStackEntry.arguments?.getString("selectedMethod") ?: ""
            val chosenAmount = backStackEntry.arguments?.getFloat("chosenAmount") ?: 0.0f

            ThirdTopUpScreen(selectedMethod = selectedMethod, chosenAmount = chosenAmount){ route, ifWorks ->
                if(route == "topUp4") navController.navigate("topUp4/$selectedMethod/$chosenAmount/$ifWorks")
                else navController.popBackStack()

            }
        }

        composable(
            route = "topUp4/{selectedMethod}/{chosenAmount}/{ifWorks}",
            arguments = listOf(
                navArgument("selectedMethod") { type = NavType.StringType },
                navArgument("chosenAmount") { type = NavType.FloatType },
                navArgument("ifWorks") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val selectedMethod = backStackEntry.arguments?.getString("selectedMethod") ?: ""
            val chosenAmount = backStackEntry.arguments?.getFloat("chosenAmount") ?: 0.0f
            val ifWorks = backStackEntry.arguments?.getString("ifWorks") ?: ""

            TopUpSuccessScreen(selectedMethod = selectedMethod, chosenAmount = chosenAmount, ifWorks = ifWorks)
            { route ->
                if(route == "back") navController.navigate("home")
                else navController.popBackStack()
            }
        }
    }
}



