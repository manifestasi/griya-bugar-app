package com.griya.griyabugar.ui.screen.main.myaccount.changepassword.enternewpass

import androidx.lifecycle.ViewModel
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.respository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EnterNewPassViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){

    fun changePassword(password: String, oldPassword: String): Flow<Resource<String>> {
        return authRepository.changePassword(password, oldPassword)
    }
}