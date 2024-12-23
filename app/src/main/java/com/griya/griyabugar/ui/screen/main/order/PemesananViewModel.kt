package com.griya.griyabugar.ui.screen.main.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.data.respository.PemesananRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PemesananViewModel @Inject constructor(
    private val pemesananRepository: PemesananRepository
):ViewModel() {

    /*
    *  untuk fetch
    * */
    private val _data_pemesanan = MutableStateFlow<Resource<List<ItemPemesananModel>?>>(Resource.Loading)
    val pemesananData : StateFlow<Resource<List<ItemPemesananModel>?>> get() = _data_pemesanan

    /*
    * untuk update
    * */
    private val _updateResult = MutableStateFlow<Resource<Boolean>>(Resource.Empty)
    val updateResult : StateFlow<Resource<Boolean>> get() = _updateResult

    init {
        fetchPemesananData()
    }

    /*
    * untuk fetch all data pemesanan
    * */
     private fun fetchPemesananData(){
        viewModelScope.launch {
            pemesananRepository.getPemesananData().collect{
                result ->
                _data_pemesanan.value = result
            }
        }
    }


    /*
    * function untuk update data
    * */
    fun updateData(uuid:String,
                   field:String,
                   new_value:Any
                   ){

        viewModelScope.launch {
            pemesananRepository.updateDataPemesanan(
                uuid,
                field,
                new_value
            ).collect{
                result ->
                _updateResult.value = result
            }
        }
    }



}