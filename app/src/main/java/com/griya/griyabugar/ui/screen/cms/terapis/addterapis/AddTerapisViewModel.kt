package com.griya.griyabugar.ui.screen.cms.terapis.addterapis

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.DataService
import com.griya.griyabugar.data.respository.LayananRepository
import com.griya.griyabugar.data.respository.TerapisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTerapisViewModel @Inject constructor(
    private val terapisRepository: TerapisRepository,
    private val layananRepository: LayananRepository
) : ViewModel() {

    fun addTerapis(
        nama: String,
        jam_pulang: String,
        jam_masuk: String,
        foto_depan: Uri,
        foto_detail: Uri,
        layanan: List<String>,
        hari_kerja: List<String>,
    ): Flow<UploadResult<String>> {
        return terapisRepository.addTerapis(
            nama,
            jam_pulang,
            jam_masuk,
            foto_depan,
            foto_detail,
            layanan,
            hari_kerja
        )
    }

    private val _dataService = MutableStateFlow<List<DataService>>(emptyList())
    val dataService: StateFlow<List<DataService>> = _dataService

    fun getAlllayanan(){
        viewModelScope.launch {
            layananRepository.getAlllayanan().collect { event ->
                when(event){
                    is Resource.Success -> {
                        _dataService.value = event.data
                    }
                    else -> {}
                }
            }
        }
    }
}