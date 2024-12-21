package com.griya.griyabugar.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataUser
import com.griya.griyabugar.data.respository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginEvent = MutableSharedFlow<Resource<DataUser?>>()
    val loginEvent: SharedFlow<Resource<DataUser?>> = _loginEvent

    fun loginAccunt(email: String, password: String){
        viewModelScope.launch {
            _loginEvent.emitAll(
                authRepository.loginAccount(email, password)
            )
        }
    }
}