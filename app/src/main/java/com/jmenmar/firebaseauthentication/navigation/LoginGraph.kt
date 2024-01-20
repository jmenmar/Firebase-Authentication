package com.jmenmar.firebaseauthentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jmenmar.firebaseauthentication.ui.screens.forgot.ForgotPasswordScreen
import com.jmenmar.firebaseauthentication.ui.screens.login.LoginScreen
import com.jmenmar.firebaseauthentication.ui.screens.signup.SignUpScreen

fun NavGraphBuilder.loginGraph(navController: NavHostController) {
    navigation(
        route = Graph.LOGIN,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                navigateToHome = {
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN)
                },
                navigateToForgot = {
                    navController.navigate(AuthScreen.Forgot.route)
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

        composable(route = AuthScreen.Forgot.route) {
            ForgotPasswordScreen(
                navigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.Login.route)
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
    object Forgot : AuthScreen(route = "FORGOT")
}