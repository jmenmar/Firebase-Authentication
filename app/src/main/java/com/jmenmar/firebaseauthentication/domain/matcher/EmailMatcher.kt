package com.jmenmar.firebaseauthentication.domain.matcher

interface EmailMatcher {
    fun isValid(email: String): Boolean
}