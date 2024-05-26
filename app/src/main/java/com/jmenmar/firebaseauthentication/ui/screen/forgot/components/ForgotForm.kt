package com.jmenmar.firebaseauthentication.ui.screen.forgot.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.ui.component.CustomTextField
import com.jmenmar.firebaseauthentication.ui.screen.forgot.ForgotEvent
import com.jmenmar.firebaseauthentication.ui.screen.forgot.ForgotState

@Composable
fun ForgotForm(
    state: ForgotState,
    onEvent: (ForgotEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = state.email,
            onValueChange = { onEvent(ForgotEvent.EmailChange(it)) },
            placeholder = "Email",
            contentDescription = "Enter email",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            leadingIcon = Icons.Outlined.Email,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onAny = {
                focusManager.clearFocus()
                onEvent(ForgotEvent.Recover)
            }),
            isError = state.emailError != null,
            errorMessage = state.emailError,
            isEnabled = !state.isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            enabled = !state.isLoading,
            onClick = {
                onEvent(ForgotEvent.Recover)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.reset_password))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { onEvent(ForgotEvent.Login) }) {
            Text(text = "Return to login")
        }
    }
}