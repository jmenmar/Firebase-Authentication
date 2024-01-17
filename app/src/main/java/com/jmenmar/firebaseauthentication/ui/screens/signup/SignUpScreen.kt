package com.jmenmar.firebaseauthentication.ui.screens.signup

import android.util.Patterns
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.ui.navigation.Graph

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel
) {
    val loading = signUpViewModel.loading.collectAsState()
    val email = signUpViewModel.email.collectAsState()
    val password = signUpViewModel.password.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loading.value) {
            CircularProgressIndicator()
        } else {
            Text(text = "Create new account")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email.value,
                label = { Text(text = stringResource(R.string.username)) },
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
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = email.value.isNotEmpty() && password.value.isNotEmpty() &&  Patterns.EMAIL_ADDRESS.matcher(email.value).matches(),
                onClick = { },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = stringResource(R.string.sign_up))
            }
        }
    }
}