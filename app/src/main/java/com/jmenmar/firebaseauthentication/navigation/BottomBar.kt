package com.jmenmar.firebaseauthentication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBar(
        route = "HOME",
        title = "Home",
        icon = Icons.Filled.Home
    )

    object Settings : BottomBar(
        route = "SETTINGS",
        title = "Settings",
        icon = Icons.Filled.Settings
    )
}