package com.griya.griyabugar.ui.screen.layanan

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.LayananModel
import com.griya.griyabugar.data.respository.LayananRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LayananViewModel @Inject constructor(
    private val layananRepository:LayananRepository
) :ViewModel() {

    private val _layananState = MutableStateFlow<Map<String, Resource<LayananModel>>>(emptyMap())
    val layananState : StateFlow<Map<String, Resource<LayananModel>>> get() = _layananState

    fun fetchById(id: String) {
        val currentState = _layananState.value[id]
        if (currentState is Resource.Success || currentState is Resource.Loading) {
            return
        }

        viewModelScope.launch {
            layananRepository.getLayananById(id).collect { result ->
                _layananState.value = _layananState.value.toMutableMap().apply {
                    this[id] = result
                }
            }
        }
    }


}