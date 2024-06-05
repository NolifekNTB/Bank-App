package com.example.bankapp.core.presentation.Navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bankapp.R
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

    val startDestination = if (viewState is AuthViewState.Success) "homeGraph" else "login"

    Scaffold(
        bottomBar = { if (viewState is AuthViewState.Success) BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavigationHost(navController, startDestination, innerPadding, authViewModel, auth)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.History,
        Screen.Pay,
        Screen.Card,
        Screen.Profile
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: String,
    innerPadding: PaddingValues,
    authViewModel: AuthViewModel,
    auth: FirebaseAuth,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("login") { LoginScreen(navController, authViewModel) }
        homeNavGraph(auth = auth, navController = navController)
        composable("history") { HistoryScreen() }
        composable("pay") { PayScreen() }
        composable("card") { CardScreen() }
        composable("profile") { ProfileScreen() }
    }
}

sealed class Screen(val route: String, val resourceId: Int, val icon: ImageVector) {
    data object Home : Screen("home", R.string.home, Icons.Filled.Home)
    data object History : Screen("history", R.string.history, Icons.Filled.Notifications) //TODO: Icon
    data object Pay : Screen("pay", R.string.pay, Icons.Filled.AccountBox) //TODO: Icon
    data object Card : Screen("card", R.string.card, Icons.Filled.Call) //TODO: Icon
    data object Profile : Screen("profile", R.string.profile, Icons.Filled.Person)
}

@Composable
fun HistoryScreen() {
    // Your HistoryScreen implementation here
}

@Composable
fun PayScreen() {
    // Your PayScreen implementation here
}

@Composable
fun CardScreen() {
    // Your CardScreen implementation here
}

@Composable
fun ProfileScreen() {
    // Your ProfileScreen implementation here
}