package com.jmenmar.firebaseauthentication.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.jmenmar.firebaseauthentication.data.repository.AuthRepositoryImpl

@Composable
fun RootNavGraph(navController: NavHostController, context: Context) {
    val authManager = AuthRepositoryImpl(context)
    val user: FirebaseUser? = authManager.getCurrentUser()

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = if(user == null) Graph.LOGIN else Graph.MAIN
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