package com.jmenmar.firebaseauthentication.ui.screens.navigation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jmenmar.firebaseauthentication.R

@Composable
fun SettingsScreen(
    innerPadding: PaddingValues,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Settings screen",
            fontSize = 32.sp
        )

        TextButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                settingsViewModel.logout()
                navigateToLogin()
            }
        ) {
            Text(
                text = stringResource(R.string.sign_out),
                fontWeight = FontWeight.Bold
            )
        }
    }
}