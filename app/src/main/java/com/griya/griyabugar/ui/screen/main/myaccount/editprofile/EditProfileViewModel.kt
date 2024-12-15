package com.griya.griyabugar.ui.screen.main.myaccount.editprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.DataUser
import com.griya.griyabugar.data.respository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val dataProfile: Flow<Resource<DataUser?>> = userDataRepository.getDataProfile()

    private val _uploadDataProfile = MutableSharedFlow<UploadResult<String>>()
    val uploadDataProfileEvent: SharedFlow<UploadResult<String>> = _uploadDataProfile

    fun uploadDataProfile(
        uri: Uri?,
        email: String,
        noTelepon: String,
        nama: String,
        kelamin: String
    ){
        viewModelScope.launch {
            _uploadDataProfile.emitAll(
                userDataRepository.uploadDataProfile(
                    uri, email, noTelepon, nama, kelamin
                )
            )
        }
    }

}