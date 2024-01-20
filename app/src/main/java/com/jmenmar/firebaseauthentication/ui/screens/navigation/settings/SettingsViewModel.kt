package com.jmenmar.firebaseauthentication.ui.screens.navigation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmenmar.firebaseauthentication.data.network.AuthRepo
import com.jmenmar.firebaseauthentication.data.network.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.signOut()
        }
    }
}