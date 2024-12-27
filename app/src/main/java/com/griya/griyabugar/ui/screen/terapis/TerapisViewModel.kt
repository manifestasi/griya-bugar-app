package com.griya.griyabugar.ui.screen.terapis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.TerapisModel
import com.griya.griyabugar.data.respository.TerapisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TerapisViewModel @Inject constructor(
    private val terapisRepo : TerapisRepository
) :ViewModel() {

    private val _terapis = MutableStateFlow<Resource<TerapisModel>>(Resource.Empty)
    val terapis : StateFlow<Resource<TerapisModel>> get() = _terapis

    fun fetchById(id:String){
        viewModelScope.launch {
            terapisRepo.getTerapisById(id).collect{
                result ->
                _terapis.value = result
            }
        }
    }

}