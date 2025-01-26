package com.griya.griyabugar.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.griya.griyabugar.data.model.DataService
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.model.PaketModel2
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    //var layanan: List<DataService> = emptyList()

    /* Terapis */
    var layanan: List<DataService> = emptyList()
    var layananName: List<String> = emptyList()
    var hari: List<String> = emptyList()
    var namaTerapis: String = ""
    var jamDatang: String = ""
    var jamPulang: String = ""
    var fotoDepanTerapis: String = ""
    var fotoDetailTerapis: String = ""

    /* Paket */
    var paketModel: PaketModel? by mutableStateOf(null)

    var paketModel2: PaketModel2? by mutableStateOf(null)
}