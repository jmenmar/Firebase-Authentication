package com.jmenmar.firebaseauthentication.ui.screen.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.domain.model.AuthResponse
import com.jmenmar.firebaseauthentication.ui.component.MessageBar
import com.jmenmar.firebaseauthentication.ui.component.SignInGoogleButton
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToForgot: () -> Unit,
    onClickSignUp: () -> Unit
) {
    val loading = loginViewModel.loading.collectAsState()
    val email = loginViewModel.email.collectAsState()
    val password = loginViewModel.password.collectAsState()

    val messageBarState by loginViewModel.messageBarState

    var passwordVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val auth = loginViewModel.instance

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) { result ->
        when(val account = auth.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))) {
            is AuthResponse.Success -> {
                val credential = GoogleAuthProvider.getCredential(account.data.idToken, null)
                scope.launch {
                    val fireUser = auth.signInWithGoogleCredential(credential)
                    if (fireUser != null){
                        Toast.makeText(context, "Bienvenidx", Toast.LENGTH_SHORT).show()
                        navigateToHome()
                    }
                }
            }
            is AuthResponse.Error -> {
                Toast.makeText(context, "Error: ${account.errorMessage}", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Error desconocido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (loading.value) {
            CircularProgressIndicator()
        } else {
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
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(64.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email.value,
                    label = { Text(text = stringResource(R.string.email)) },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { loginViewModel.setEmail(it) })

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
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

                TextButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { navigateToForgot() }
                ) {
                    Text(text = stringResource(R.string.forgot_password))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    enabled = loginViewModel.isValidForm(),
                    onClick = {
                        loginViewModel.login {
                            navigateToHome()
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.login))
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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

                SignInGoogleButton(onClick = { auth.signInWithGoogle(googleSignInLauncher) })

                Spacer(modifier = Modifier.weight(1f))

                Row (verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.dont_have_account))
                    TextButton(onClick = { onClickSignUp() }) {
                        Text(text = stringResource(R.string.sign_up))
                    }
                }
            }
        }
    }
}