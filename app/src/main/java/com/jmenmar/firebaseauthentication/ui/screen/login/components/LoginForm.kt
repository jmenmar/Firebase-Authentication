package com.jmenmar.firebaseauthentication.ui.screen.login.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.ui.component.CustomPasswordTextField
import com.jmenmar.firebaseauthentication.ui.component.CustomTextField
import com.jmenmar.firebaseauthentication.ui.component.SignInGoogleButton
import com.jmenmar.firebaseauthentication.ui.screen.login.LoginEvent
import com.jmenmar.firebaseauthentication.ui.screen.login.LoginState
import com.jmenmar.firebaseauthentication.ui.screen.login.LoginViewModel

@Composable
fun LoginForm(
    viewModel: LoginViewModel,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onSignUp: () -> Unit,
    onForgot: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) { result ->
        viewModel.loginWithGoogleResult(activityResult = result)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        CustomTextField(
            value = state.email,
            onValueChange = { onEvent(LoginEvent.EmailChange(it)) },
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
            errorMessage = state.emailError,
            isEnabled = !state.isLoading
        )

        Spacer(modifier = Modifier.height(4.dp))

        CustomPasswordTextField(
            value = state.password,
            onValueChange = { onEvent(LoginEvent.PasswordChange(it)) },
            contentDescription = "Enter password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            errorMessage = state.passwordError,
            isEnabled = !state.isLoading,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onAny = {
                focusManager.clearFocus()
                onEvent(LoginEvent.Login())
            })
        )

        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = { onForgot() }
        ) {
            Text(text = stringResource(R.string.forgot_password))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            enabled = !state.isLoading,
            onClick = {
                onEvent(LoginEvent.Login())
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.login))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.or).uppercase(),
                fontSize = 12.sp
            )
            Divider(modifier = Modifier.weight(1f))
        }

        SignInGoogleButton(
            onClick = { viewModel.loginWithGoogleLauncher(launcher = googleSignInLauncher) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.dont_have_account))
            TextButton(
                onClick = {
                    onSignUp()
                }
            ) {
                Text(text = stringResource(R.string.sign_up))
            }
        }
    }
}