package com.jmenmar.firebaseauthentication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.jmenmar.firebaseauthentication.ui.navigation.RootNavigationGraph
import com.jmenmar.firebaseauthentication.ui.theme.FirebaseAuthenticationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAuthenticationTheme{
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}