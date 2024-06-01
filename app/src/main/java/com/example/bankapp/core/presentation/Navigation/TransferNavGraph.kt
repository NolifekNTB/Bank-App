package com.example.bankapp.core.presentation.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bankapp.home.presentation.screens.Transfer.Transfer
import com.example.bankapp.home.presentation.screens.topUp.TopUpScreen
import com.example.bankapp.home.presentation.screens.topUp.TopUpViewModel

private object RoutesTransfer {
    const val TRANSFER_GRAPH = "TransferGraph"
    const val TRANSFER = "transfer"
    const val TRANSFER2 = "transfer2"
}

fun NavGraphBuilder.transferNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = RoutesTransfer.TRANSFER_GRAPH,
        startDestination = RoutesTransfer.TRANSFER
    ) {
        composable(route = RoutesTransfer.TRANSFER) {
            Transfer() { backOrGo ->
                if (backOrGo == "back") navController.popBackStack()
                else navController.navigate(RoutesTransfer.TRANSFER2)
            }
        }

    }
}