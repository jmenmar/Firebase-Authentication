package com.jmenmar.firebaseauthentication.ui.screen.forgot

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.ui.component.MessageBar

@Composable
fun ForgotPasswordScreen(
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {
    val email by forgotPasswordViewModel.email.collectAsState()
    val messageBarState by forgotPasswordViewModel.messageBarState

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MessageBar(messageBarState = messageBarState)
        }

        Column(
            modifier = Modifier.weight(9f).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                label = { Text(text = stringResource(R.string.email)) },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { forgotPasswordViewModel.setEmail(it) })

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                enabled = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches(),
                onClick = {
                    forgotPasswordViewModel.resetPassword {
                        navigateToLogin()
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.reset_password))
            }
        }
    }
}