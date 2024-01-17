package com.jmenmar.firebaseauthentication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.LOGIN
    ) {
        loginGraph(navController = navController)
        composable(route = Graph.MAIN) {
            MainScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val LOGIN = "login_graph"
    const val MAIN = "main_graph"
}