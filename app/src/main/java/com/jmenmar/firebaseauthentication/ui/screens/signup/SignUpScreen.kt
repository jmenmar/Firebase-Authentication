package com.jmenmar.firebaseauthentication.ui.screens.signup

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jmenmar.firebaseauthentication.R

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    val loading = signUpViewModel.loading.collectAsState()
    val email = signUpViewModel.email.collectAsState()
    val password = signUpViewModel.password.collectAsState()
    val repeatPassword = signUpViewModel.repeatPassword.collectAsState()
    val error = signUpViewModel.error.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loading.value) {
            CircularProgressIndicator()
        } else {
            Text(
                text = stringResource(id = R.string.sign_up),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email.value,
                label = { Text(text = stringResource(R.string.email)) },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { signUpViewModel.setEmail(it) })

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = password.value,
                label = { Text(text = stringResource(R.string.password)) },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { signUpViewModel.setPassword(it) },
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = repeatPassword.value,
                label = { Text(text = stringResource(R.string.repeat_password)) },
                maxLines = 1,
                singleLine = true,
                isError = !signUpViewModel.passwordsMatch(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { signUpViewModel.setRepeatPassword(it) },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                enabled = signUpViewModel.isValidForm(),
                onClick = {
                    signUpViewModel.signUp {
                        navigateToHome()
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.sign_up))
            }
        }
    }
}