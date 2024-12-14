package com.griya.griyabugar.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.griya.griyabugar.data.respository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

}