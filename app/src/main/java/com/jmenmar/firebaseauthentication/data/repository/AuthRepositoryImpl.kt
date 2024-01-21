package com.jmenmar.firebaseauthentication.data.repository

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.domain.model.AuthResponse
import com.jmenmar.firebaseauthentication.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): AuthRepository {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    private val signInClient = Identity.getSignInClient(context)

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    // EMAIL & PASSWORD
    override suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResponse<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthResponse.Success(authResult.user)
        } catch(e: Exception) {
            AuthResponse.Error(e.message ?: context.getString(R.string.error_sign_up))
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResponse<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResponse.Success(authResult.user)
        } catch(e: Exception) {
            AuthResponse.Error(e.message ?: context.getString(R.string.incorrect_email_password))
        }
    }

    override suspend fun resetPassword(email: String): AuthResponse<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            AuthResponse.Success(Unit)
        } catch(e: Exception) {
            AuthResponse.Error(e.message ?: context.getString(R.string.an_error_has_occurred))
        }
    }

    // GOOGLE SIGN IN
    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    override fun handleSignInResult(task: Task<GoogleSignInAccount>): AuthResponse<GoogleSignInAccount>? {
        return try {
            val account = task.getResult(ApiException::class.java)
            AuthResponse.Success(account)
        } catch (e: ApiException) {
            AuthResponse.Error(e.message ?: context.getString(R.string.an_error_has_occurred))
        }
    }

    override suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthResponse<FirebaseUser>? {
        return try {
            val firebaseUser = firebaseAuth.signInWithCredential(credential).await()
            firebaseUser.user?.let {
                AuthResponse.Success(it)
            } ?: throw Exception("Sign in with Google failed.")
        } catch (e: Exception) {
            AuthResponse.Error(e.message ?: context.getString(R.string.an_error_has_occurred))
        }
    }

    override fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    // SIGN OUT
    override fun signOut() {
        firebaseAuth.signOut()
        signInClient.signOut()
    }
}