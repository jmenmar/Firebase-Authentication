package com.jmenmar.firebaseauthentication.ui.screens.navigation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.jmenmar.firebaseauthentication.data.network.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authService.logout()
        }
    }
}