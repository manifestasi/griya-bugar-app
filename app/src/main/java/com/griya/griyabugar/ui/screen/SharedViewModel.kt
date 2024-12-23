package com.griya.griyabugar.ui.screen

import androidx.lifecycle.ViewModel
import com.griya.griyabugar.data.model.DataService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    var layanan: List<DataService>? = null
    var hari: List<String>? = null
}