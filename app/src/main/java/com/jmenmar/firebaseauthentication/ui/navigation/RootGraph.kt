package com.jmenmar.firebaseauthentication.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.jmenmar.firebaseauthentication.data.network.AuthManager
import com.jmenmar.firebaseauthentication.data.network.AuthRepo

@Composable
fun RootNavGraph(navController: NavHostController, context: Context) {
    val authManager = AuthManager(context)
    val authRepo = AuthRepo(context)

    val user: FirebaseUser? = authRepo.getCurrentUser()

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = if(user == null) Graph.LOGIN else Graph.MAIN
    ) {
        loginGraph(navController = navController, context = context, auth = authRepo)

        composable(route = Graph.MAIN) {
            MainScreen(context = context, auth = authRepo)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val LOGIN = "login_graph"
    const val MAIN = "main_graph"
}