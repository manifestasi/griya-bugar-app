package com.griya.griyabugar.ui.screen.paket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.respository.PaketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PaketViewModel @Inject constructor(
    val paketRepo : PaketRepository
) : ViewModel() {

    private val _paketState = MutableStateFlow<Map<String, Resource<PaketModel>>>(emptyMap())
    val paketState : StateFlow<Map<String, Resource<PaketModel>>> get() = _paketState

    fun fetchById(id:String) {
        viewModelScope.launch {
            paketRepo.getPaketById(id).collect{
                result ->
                _paketState.value = _paketState.value.toMutableMap().apply {
                    this[id] = result
                }
            }
        }
    }

}