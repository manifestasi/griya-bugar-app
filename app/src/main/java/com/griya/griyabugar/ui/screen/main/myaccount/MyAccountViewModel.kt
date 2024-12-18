package com.griya.griyabugar.ui.screen.main.myaccount

import androidx.lifecycle.ViewModel
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.respository.AuthRepository
import com.griya.griyabugar.data.respository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataRepository: UserDataRepository
): ViewModel() {

    val getDataProfile = userDataRepository.getDataProfile()

    fun logoutAccount(): Flow<Resource<Boolean>> {
        return authRepository.logoutAccount()
    }
}