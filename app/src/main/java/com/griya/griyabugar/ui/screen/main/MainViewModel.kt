package com.griya.griyabugar.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataUser
import com.griya.griyabugar.data.respository.NotificationRepository
import com.griya.griyabugar.data.respository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _dataUser = MutableStateFlow<Resource<DataUser?>>(Resource.Loading)
    val dataUser: StateFlow<Resource<DataUser?>> = _dataUser

    fun fetchDataUser(){
        viewModelScope.launch {
            userDataRepository.getDataProfile().collect {
                _dataUser.value = it
            }
        }
    }

    fun getDataUser() = userDataRepository.getDataProfile()

    fun countNotificationUnread() = notificationRepository.countNotificationUnread()
}