package com.jmenmar.firebaseauthentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jmenmar.firebaseauthentication.ui.screen.forgot.ForgotPasswordScreen
import com.jmenmar.firebaseauthentication.ui.screen.login.LoginScreen
import com.jmenmar.firebaseauthentication.ui.screen.signup.SignUpScreen

fun NavGraphBuilder.loginGraph(navController: NavHostController) {
    navigation(
        route = Graph.LOGIN,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                onLogin = {
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN)
                },
                onForgot = {
                    navController.navigate(AuthScreen.Forgot.route)
                },
                onSignUp = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }

        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(
                onSignIn = {
                    navController.navigate(Graph.MAIN) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                onLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = AuthScreen.Forgot.route) {
            ForgotPasswordScreen(
                onLogin = {
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
