package com.griya.griyabugar.ui.screen.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataNotificationModel
import com.griya.griyabugar.data.respository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifikasiViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _dataNotification = MutableStateFlow<Resource<List<DataNotificationModel>>>(Resource.Loading)
    val dataNotification: StateFlow<Resource<List<DataNotificationModel>>> = _dataNotification

    fun fetchGetAllNotification() {
        viewModelScope.launch {
            notificationRepository.getAllNotification()
                .catch { e ->
                    _dataNotification.emit(Resource.Error(e.message ?: "Unknown error"))
                }
                .collect { event ->
                    _dataNotification.emit(event)
                }
        }
    }

    fun deleteNotification(uidNotification: String) = notificationRepository.deleteNotification(
        uidNotification = uidNotification
    )

}