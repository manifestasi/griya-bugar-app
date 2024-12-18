package com.griya.griyabugar.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.respository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _registerEvent = MutableSharedFlow<Resource<String>>()
    val registerEvent: SharedFlow<Resource<String>> = _registerEvent

    fun RegisterCustomer(
        nama: String,
        noTelepon: String,
        email: String,
        password: String,
        confirmPass: String
    ){
        viewModelScope.launch {
            _registerEvent.emitAll(
                authRepository.registerCustomer(nama, noTelepon, email, password, confirmPass)
            )
        }
    }
}