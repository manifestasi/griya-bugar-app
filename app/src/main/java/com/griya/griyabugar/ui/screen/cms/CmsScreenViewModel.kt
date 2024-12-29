package com.griya.griyabugar.ui.screen.cms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.respository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CmsScreenViewModel @Inject constructor(
    val authRepo : AuthRepository
) :ViewModel() {

    /*
    * untuk signout
    * */
    private val _logout_state = MutableStateFlow<Resource<Boolean>>(Resource.Empty)
    val logout_state get() = _logout_state

    /*
    * signout akun
    * */
    fun signout(){
        viewModelScope.launch {
            authRepo.logoutAccount().collect{
                result->
                _logout_state.value = result
            }
        }
    }
}