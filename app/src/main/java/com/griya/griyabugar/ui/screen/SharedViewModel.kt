package com.griya.griyabugar.ui.screen

import androidx.lifecycle.ViewModel
import com.griya.griyabugar.data.model.DataService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    var layanan: List<DataService> = emptyList()
    var hari: List<String> = emptyList()
    var namaTerapis: String = ""
    var jamDatang: String = ""
    var jamPulang: String = ""
    var fotoDepanTerapis: String = ""
    var fotoDetailTerapis: String = ""
}