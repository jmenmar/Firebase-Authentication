package com.jmenmar.firebaseauthentication.ui.screen.signup.components

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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.ui.component.CustomPasswordTextField
import com.jmenmar.firebaseauthentication.ui.component.CustomTextField
import com.jmenmar.firebaseauthentication.ui.screen.signup.SignUpEvent
import com.jmenmar.firebaseauthentication.ui.screen.signup.SignUpState

@Composable
fun SignUpForm(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = state.email,
            onValueChange = { onEvent(SignUpEvent.EmailChange(it)) },
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
                focusManager.moveFocus(FocusDirection.Next)
            }),
            isError = state.emailError != null,
            errorMessage = state.emailError,
            isEnabled = !state.isLoading
        )

        Spacer(modifier = Modifier.height(4.dp))

        CustomPasswordTextField(
            value = state.password,
            onValueChange = { onEvent(SignUpEvent.PasswordChange(it)) },
            contentDescription = "Enter password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            isEnabled = !state.isLoading,
            isError = state.passwordError != null,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onAny = {
                focusManager.clearFocus()
                onEvent(SignUpEvent.SignUp)
            })
        )

        Spacer(modifier = Modifier.height(4.dp))

        CustomPasswordTextField(
            value = state.repeatPassword,
            onValueChange = { onEvent(SignUpEvent.RepeatPasswordChange(it)) },
            contentDescription = "Enter password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            isEnabled = !state.isLoading,
            isError = state.passwordError != null,
            errorMessage = state.passwordError,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onAny = {
                focusManager.clearFocus()
                onEvent(SignUpEvent.SignUp)
            })
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            enabled = !state.isLoading,
            onClick = {
                onEvent(SignUpEvent.SignUp)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.sign_up))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { onEvent(SignUpEvent.LogIn) }) {
            Text(
                text = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Sign in")
                    }
                }
            )
        }
    }
}