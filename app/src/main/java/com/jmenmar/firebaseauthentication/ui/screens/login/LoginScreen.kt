package com.jmenmar.firebaseauthentication.ui.screens.login

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jmenmar.firebaseauthentication.R

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    onClickSignUp: () -> Unit
) {
    val loading = loginViewModel.loading.collectAsState()
    val email = loginViewModel.email.collectAsState()
    val password = loginViewModel.password.collectAsState()
    val error = loginViewModel.error.collectAsState()

    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loading.value) {
            CircularProgressIndicator()
        } else {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(64.dp))
            OutlinedTextField(
                value = email.value,
                label = { Text(text = stringResource(R.string.email)) },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { loginViewModel.setEmail(it) })
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = password.value,
                label = { Text(text = stringResource(R.string.password)) },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { loginViewModel.setPassword(it) },
                trailingIcon = {
                    val icon = if (passwordVisibility) {
                        R.drawable.ic_visibility_off
                    } else {
                        R.drawable.ic_visibility_on
                    }
                    IconButton(onClick = { passwordVisibility = !passwordVisibility}) {
                        Icon(painter = painterResource(icon), contentDescription = icon.toString())
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = error.value, color = Color.Red, fontStyle = FontStyle.Italic)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = email.value.isNotEmpty() && password.value.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.value).matches(),
                onClick = {
                    loginViewModel.login {
                        navigateToHome()
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = stringResource(R.string.login))
            }
            TextButton(onClick = { onClickSignUp() }) {
                Text(text = stringResource(R.string.create_account))
            }
        }

        LaunchedEffect(key1 = Unit) {
            if (loginViewModel.isUserLogged()) {
                navigateToHome()
            }
        }
    }
}