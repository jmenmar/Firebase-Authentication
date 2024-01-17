package com.jmenmar.firebaseauthentication.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jmenmar.firebaseauthentication.ui.screens.login.LoginScreen
import com.jmenmar.firebaseauthentication.ui.screens.signup.SignUpScreen
import com.jmenmar.firebaseauthentication.ui.screens.login.LoginViewModel
import com.jmenmar.firebaseauthentication.ui.screens.signup.SignUpViewModel

fun NavGraphBuilder.loginGraph(navController: NavHostController) {
    navigation(
        route = Graph.LOGIN,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()

            LoginScreen(
                loginViewModel = loginViewModel,
                onClickLogin = {
                    navController.popBackStack()
                    navController.navigate(Graph.MAIN)
                },
                onClickSignUp = { navController.navigate(AuthScreen.SignUp.route) }
            )
        }

        composable(route = AuthScreen.SignUp.route) {
            val signUpViewModel: SignUpViewModel = hiltViewModel()

            SignUpScreen(signUpViewModel = signUpViewModel)
        }
    }
}

sealed class AuthScreen(
    val route: String
) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGNUP")
}
