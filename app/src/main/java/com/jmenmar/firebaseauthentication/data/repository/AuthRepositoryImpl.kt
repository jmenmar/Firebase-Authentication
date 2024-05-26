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
    override suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(authResult.user)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(authResult.user)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch(e: Exception) {
            Result.failure(e)
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

    override fun signInWithGoogleResult(task: Task<GoogleSignInAccount>): Result<GoogleSignInAccount> {
        return try {
            val account = task.getResult(ApiException::class.java)
            Result.success(account)
        } catch (e: ApiException) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithGoogleCredential(credential: AuthCredential): Result<FirebaseUser> {
        return try {
            val firebaseUser = firebaseAuth.signInWithCredential(credential).await()
            firebaseUser.user?.let {
                Result.success(it)
            } ?: throw Exception("Sign in with Google failed.")
        } catch (e: Exception) {
            Result.failure(e)
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