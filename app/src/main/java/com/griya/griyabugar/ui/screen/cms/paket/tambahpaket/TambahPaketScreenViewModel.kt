package com.griya.griyabugar.ui.screen.cms.paket.tambahpaket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.Layanan
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.model.PaketModelWithLayanan
import com.griya.griyabugar.data.respository.PaketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TambahPaketScreenViewModel @Inject constructor(
    private val paketRepository: PaketRepository
) : ViewModel() {

    private val _layananState = MutableStateFlow<Resource<List<Layanan>>>(Resource.Empty)
    val layananState: StateFlow<Resource<List<Layanan>>> = _layananState

    private val _uiState = MutableStateFlow<Resource<Unit>>(Resource.Empty)
    val uiState: StateFlow<Resource<Unit>> = _uiState

    // Fungsi untuk memuat layanan
    fun loadLayanan() {
        viewModelScope.launch {
            _layananState.value = Resource.Loading
            _layananState.value = paketRepository.getLayanan()
        }
    }

    // Fungsi untuk menambahkan paket
    fun addPaket(paket: PaketModel) {
        viewModelScope.launch {
            _uiState.value = Resource.Loading
            _uiState.value = paketRepository.addPaket(paket)
        }
    }
}
