package com.jmenmar.firebaseauthentication.ui.navigation

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jmenmar.firebaseauthentication.data.network.AuthManager
import com.jmenmar.firebaseauthentication.data.network.AuthRepo
import com.jmenmar.firebaseauthentication.ui.screens.login.LoginScreen
import com.jmenmar.firebaseauthentication.ui.screens.signup.SignUpScreen

fun NavGraphBuilder.loginGraph(navController: NavHostController, context: Context, auth: AuthRepo) {
    navigation(
        route = Graph.LOGIN,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                auth = auth,
                navigateToHome = {
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN)
                },
                onClickSignUp = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }

        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(
                navigateToHome = {
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN)
                }
            )
        }
    }
}

sealed class AuthScreen(
    val route: String
) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGNUP")
}
