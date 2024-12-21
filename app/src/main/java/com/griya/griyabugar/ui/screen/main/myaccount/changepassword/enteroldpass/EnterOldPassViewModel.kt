package com.griya.griyabugar.ui.screen.main.myaccount.changepassword.enteroldpass

import androidx.lifecycle.ViewModel
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.respository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EnterOldPassViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun verifyOldPassword(password: String): Flow<Resource<String>> {
        return authRepository.verifyOldPassword(password)
    }
}