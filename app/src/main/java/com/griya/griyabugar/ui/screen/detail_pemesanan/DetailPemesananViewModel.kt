package com.griya.griyabugar.ui.screen.detail_pemesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.data.respository.PemesananRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPemesananViewModel @Inject constructor(
    private val pemesanan_repo: PemesananRepository
) : ViewModel() {

    private val _detail_data = MutableStateFlow<Map<String, Resource<ItemPemesananModel>>> (emptyMap())
    val detail_data : StateFlow<Map<String, Resource<ItemPemesananModel>>> get() = _detail_data


    fun fetchDetailById(uuid:String){
        val currentState = _detail_data.value[uuid]

        if(currentState is Resource.Success || currentState is Resource.Loading){
            return
        }

        viewModelScope.launch {
            pemesanan_repo.getPemesananById(uuid_doc = uuid).collect{
                result ->
                _detail_data.value = _detail_data.value.toMutableMap().apply {
                    this[uuid] = result
                }
            }
        }
    }

}