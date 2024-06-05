package com.example.bankapp.core.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.bankapp.home.presentation.Transactions.screens.TransactionsSecond
import com.example.bankapp.home.presentation.Transactions.screens.TransactionsThird
import com.example.bankapp.home.presentation.Transactions.screens.TransactionsFirst
import com.example.bankapp.home.presentation.Transactions.screens.TransactionsSuccess
import com.example.bankapp.home.presentation.Transactions.TransactionsViewModel
import org.koin.androidx.compose.getViewModel

object RoutesTransactions {
    const val TRANSACTIONS_GRAPH = "Transactions/{route}"
    const val TRANSACTIONS_FIRST = "TransactionsFirst"
    const val TRANSACTIONS_SECOND = "TransactionsSecond"
    const val TRANSACTIONS_THIRD = "TransactionsThird"
    const val TRANSACTIONS_SUCCESS = "TransactionsSuccess"
}

fun NavGraphBuilder.transactionsNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = RoutesTransactions.TRANSACTIONS_GRAPH,
        startDestination = RoutesTransactions.TRANSACTIONS_FIRST
    ) {
        composable(
            route = RoutesTransactions.TRANSACTIONS_FIRST,
            arguments = listOf(navArgument("route") { type = NavType.StringType })
        ) { entry ->
            val route = entry.arguments?.getString("route") ?: ""
            val transactionsViewModel = entry.sharedViewModelSearch<TransactionsViewModel>(navController, RoutesTransactions.TRANSACTIONS_GRAPH)

            TransactionsFirst(transactionsViewModel = transactionsViewModel, whichScreen = route) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTransactions.TRANSACTIONS_SECOND)
            }
        }

        composable(route = RoutesTransactions.TRANSACTIONS_SECOND) { entry ->
            val transactionsViewModel = entry.sharedViewModelSearch<TransactionsViewModel>(navController, RoutesTransactions.TRANSACTIONS_GRAPH)

            TransactionsSecond(transactionsViewModel = transactionsViewModel) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTransactions.TRANSACTIONS_THIRD)
            }
        }

        composable(route = RoutesTransactions.TRANSACTIONS_THIRD) { entry ->
            val transactionsViewModel = entry.sharedViewModelSearch<TransactionsViewModel>(navController, RoutesTransactions.TRANSACTIONS_GRAPH)

            TransactionsThird(transactionsViewModel = transactionsViewModel) { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTransactions.TRANSACTIONS_SUCCESS)
            }
        }

        composable(route = RoutesTransactions.TRANSACTIONS_SUCCESS) { entry ->
            val transactionsViewModel = entry.sharedViewModelSearch<TransactionsViewModel>(navController, RoutesTransactions.TRANSACTIONS_GRAPH)

            TransactionsSuccess(transactionsViewModel = transactionsViewModel)
            { backOrGo ->
                if (backOrGo == "backe" + "") navController.navigate("HomeGraph")
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