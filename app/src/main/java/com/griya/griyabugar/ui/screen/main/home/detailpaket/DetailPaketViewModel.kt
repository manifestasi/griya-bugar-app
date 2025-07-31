package com.griya.griyabugar.ui.screen.main.home.detailpaket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.respository.PemesananRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPaketViewModel @Inject constructor(
    private val pemesananRepository: PemesananRepository
) : ViewModel() {

    fun addPemesanan(
        id_paket: String,
        id_terapis: String,
        jam_pemesanan: String,
        rated: Boolean,
        tanggal_servis: String,
        kategori: String,
        paket: String
    ): Flow<Resource<String>> {
        return pemesananRepository.addPemesanan(
            id_paket, id_terapis, jam_pemesanan, rated, tanggal_servis, kategori, paket
        )
    }
}