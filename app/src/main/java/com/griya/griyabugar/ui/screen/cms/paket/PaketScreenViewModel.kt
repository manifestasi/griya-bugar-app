package com.griya.griyabugar.ui.screen.cms.paket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.model.PaketModelWithLayanan
import com.griya.griyabugar.data.respository.PaketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaketScreenViewModel @Inject constructor(
    private val paketRepository: PaketRepository
) : ViewModel() {

    // StateFlow untuk menyimpan status data paket
    private val _paketState = MutableStateFlow<Resource<List<PaketModelWithLayanan>>>(Resource.Loading)
    val paketState: StateFlow<Resource<List<PaketModelWithLayanan>>> = _paketState

    init {
        // Panggil fungsi untuk memuat data saat ViewModel diinisialisasi
        fetchPaketWithLayananNames()
    }

    private fun fetchPaketWithLayananNames() {
        viewModelScope.launch {
            paketRepository.getPaketWithLayananName().collect { result ->
                _paketState.value = result
            }
        }
    }
}
