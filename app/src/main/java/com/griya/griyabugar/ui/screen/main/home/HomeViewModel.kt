package com.griya.griyabugar.ui.screen.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataTerapis
import com.griya.griyabugar.data.model.PaketModelWithLayanan
import com.griya.griyabugar.data.respository.PaketRepository
import com.griya.griyabugar.data.respository.TerapisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val terapisRepository: TerapisRepository,
    private val paketRepository: PaketRepository
) : ViewModel() {

    init {
        fetchAllTerapis()
        fetchAllPaket()
    }

    private val _dataTerapis = MutableStateFlow<Resource<List<DataTerapis>>>(Resource.Loading)
    val dataTerapis: StateFlow<Resource<List<DataTerapis>>> = _dataTerapis

    private fun fetchAllTerapis(){
        viewModelScope.launch {
            terapisRepository.getAllTerapis().collect {
                _dataTerapis.emit(it)
            }
        }
    }

    private val _dataPaket = MutableStateFlow<Resource<List<PaketModelWithLayanan>>>(Resource.Loading)
    val dataPaket: StateFlow<Resource<List<PaketModelWithLayanan>>> = _dataPaket

    private fun fetchAllPaket(){
        viewModelScope.launch {
            paketRepository.getPaketWithLayananName().collect {
                _dataPaket.value = it
            }
        }
    }
}