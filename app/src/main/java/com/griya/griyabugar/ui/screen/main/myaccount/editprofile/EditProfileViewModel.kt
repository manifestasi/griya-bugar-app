package com.griya.griyabugar.ui.screen.main.myaccount.editprofile

import androidx.lifecycle.ViewModel
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataUser
import com.griya.griyabugar.data.respository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val dataProfile: Flow<Resource<DataUser?>> = userDataRepository.getDataProfile()

}