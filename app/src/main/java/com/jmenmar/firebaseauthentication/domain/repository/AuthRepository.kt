package com.jmenmar.firebaseauthentication.domain.repository

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.jmenmar.firebaseauthentication.domain.model.AuthResponse

interface AuthRepository {

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResponse<FirebaseUser?>

    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResponse<FirebaseUser?>

    suspend fun resetPassword(email: String): AuthResponse<Unit>

    fun signOut()

    fun getCurrentUser(): FirebaseUser?

    fun handleSignInResult(task: Task<GoogleSignInAccount>): AuthResponse<GoogleSignInAccount>?

    suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthResponse<FirebaseUser>?

    fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>)
}