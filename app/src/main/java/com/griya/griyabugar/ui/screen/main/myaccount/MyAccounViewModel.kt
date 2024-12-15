package com.griya.griyabugar.ui.screen.main.myaccount

import androidx.lifecycle.ViewModel
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.respository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyAccounViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun logoutAccount(): Flow<Resource<Boolean>> {
        return authRepository.logoutAccount()
    }
}