package com.griya.griyabugar.ui.screen.forgetPass

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
class ForgetPassViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _sendForgotPasswordLinkEvent = MutableSharedFlow<Resource<String>>()
    val sendForgotPasswordLinkEvent: SharedFlow<Resource<String>> = _sendForgotPasswordLinkEvent

    fun sendForgotPasswordLink(email: String){
        viewModelScope.launch {
            _sendForgotPasswordLinkEvent.emitAll(
                authRepository.sendForgotPasswordLink(email)
            )
        }
    }

    private val _updatePasswordWithOobCodeEvent = MutableSharedFlow<Resource<String>>()
    val updatePasswordWithOobCodeEvent: SharedFlow<Resource<String>> = _updatePasswordWithOobCodeEvent

    fun updatePasswordWithOobCode(
        oobCode: String,
        newPassword: String,
        confirmNewPassword: String
    ){
        viewModelScope.launch {
            _updatePasswordWithOobCodeEvent.emitAll(
                authRepository.updatePasswordWithOobCode(oobCode, newPassword, confirmNewPassword)
            )
        }
    }
}