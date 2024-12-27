package com.griya.griyabugar.ui.screen.cms.terapis.previewterapis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataTerapis
import com.griya.griyabugar.data.respository.TerapisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TerapisViewModel @Inject constructor(
    private val terapisRepository: TerapisRepository
) : ViewModel() {

    private val _isLoadingTerapis = MutableStateFlow(false)
    val isLoadingTerapis: StateFlow<Boolean> = _isLoadingTerapis

    private val _dataTerapis: MutableStateFlow<List<DataTerapis>> = MutableStateFlow(emptyList())
    val dataTerapis: StateFlow<List<DataTerapis>> = _dataTerapis

    private val _isErrorTerapis = MutableStateFlow(false)
    val isErrorTerapis: StateFlow<Boolean> = _isErrorTerapis

    private val _errorMessageTerapis = MutableStateFlow("")
    val errorMessageTerapis: StateFlow<String> = _errorMessageTerapis

    fun getAllTerapis(){
        viewModelScope.launch {
            terapisRepository.getAllTerapis().collect { event ->
                when (event){
                    is Resource.Loading -> {
                        _isErrorTerapis.value = false
                        _isLoadingTerapis.value = true
                    }
                    is Resource.Success -> {
                        _isErrorTerapis.value = false
                        _isLoadingTerapis.value = false
                        _dataTerapis.value = event.data
                    }
                    is Resource.Error -> {
                        _isLoadingTerapis.value = false
                        _isErrorTerapis.value = true
                        _errorMessageTerapis.value = event.errorMessage
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteTerapis(id: String) = terapisRepository.deleteTerapis(id)
}