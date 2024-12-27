package com.griya.griyabugar.ui.screen.cms.terapis.editterapis

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.DataDetailTerapisAndLayanan
import com.griya.griyabugar.data.respository.LayananRepository
import com.griya.griyabugar.data.respository.TerapisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTerapisViewModel @Inject constructor(
    private val terapisRepository: TerapisRepository,
    private val layananRepository: LayananRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _dataDetailTerapisAndLayanan = MutableStateFlow<DataDetailTerapisAndLayanan?>(null)
    val dataDetailTerapisAndLayanan: StateFlow<DataDetailTerapisAndLayanan?> = _dataDetailTerapisAndLayanan

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getDetailTerapisAndLayanan(id: String){
        viewModelScope.launch {
            terapisRepository.getDetailTerapisAndLayanan(id).collect { event ->
                when (event){
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _dataDetailTerapisAndLayanan.value = event.data
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        Toast.makeText(context, event.errorMessage, Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }
    }

    fun updateTerapis(
        id: String,
        nama: String,
        jam_pulang: String,
        jam_masuk: String,
        foto_depan: Uri?,
        foto_detail: Uri?,
        layanan: List<String>,
        hari_kerja: List<String>
    ): Flow<UploadResult<String>> = terapisRepository.updateTerapis(
        id,
        nama,
        jam_pulang,
        jam_masuk,
        foto_depan,
        foto_detail,
        layanan,
        hari_kerja
    )
}