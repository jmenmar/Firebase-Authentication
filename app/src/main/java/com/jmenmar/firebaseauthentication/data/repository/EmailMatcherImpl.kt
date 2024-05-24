package com.jmenmar.firebaseauthentication.data.repository

import android.util.Patterns
import com.jmenmar.firebaseauthentication.domain.matcher.EmailMatcher

class EmailMatcherImpl : EmailMatcher {
    override fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}