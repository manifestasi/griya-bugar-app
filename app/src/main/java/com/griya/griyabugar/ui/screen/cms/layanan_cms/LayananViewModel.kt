package com.griya.griyabugar.ui.screen.cms.layanan_cms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.LayananModel
import com.griya.griyabugar.data.respository.LayananRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LayananViewModel @Inject constructor(
    private val layananRepository:LayananRepository
) :ViewModel() {
    /*
    * untuk fetch by id
    * */
    private val _layananState = MutableStateFlow<Map<String, Resource<LayananModel>>>(emptyMap())
    val layananState : StateFlow<Map<String, Resource<LayananModel>>> get() = _layananState

    /*
    * untuk fetch all
    * */
    private val _getAlllayananState = MutableStateFlow<Resource<List<LayananModel>>>(Resource.Loading)
    val getAlllayananState : StateFlow<Resource<List<LayananModel>>> get() = _getAlllayananState

    /*
    * untuk insert
    * */

    private val _insertResult = MutableStateFlow<Resource<Boolean>>(Resource.Empty)
    val insertResult : StateFlow<Resource<Boolean>> get() = _insertResult

    /*
    * untuk update
    * */

    private val _updateResult = MutableStateFlow<Resource<Boolean>>(Resource.Empty)
    val updateResult : StateFlow<Resource<Boolean>> get() = _updateResult

    /*
    * untuk delete
    * */

    private val _deleteResult = MutableStateFlow<Resource<Boolean>>(Resource.Empty)
    val deleteResult : StateFlow<Resource<Boolean>> get() = _deleteResult




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

    fun fetchAll(){
        viewModelScope.launch {
            layananRepository.getLayananData().collect{
                result ->
                _getAlllayananState.value = result
            }
        }
    }


    /*
    * function untuk insert data
    * */
    fun insertData(
                   field:String,
                   new_value:Any
    ){

        viewModelScope.launch {
            layananRepository.insertDataPemesanan (
                field,
                new_value
            ).collect{
                    result ->
                _insertResult.value = result
            }
        }
    }

    /*
   * function untuk update data
   * */
    fun updateData(
        uuid_doc:String,
        field:String,
        new_value:Any
    ){

        viewModelScope.launch {
            layananRepository.updateDataLayanan (
                uuid_doc,
                field,
                new_value
            ).collect{
                    result ->
                _updateResult.value = result
            }
        }
    }

    /*
   * function untuk update data
   * */
    fun deleteData(
        uuid_doc:String
    ){

        viewModelScope.launch {
            layananRepository.deleteDataLayanan (
                uuid_doc
            ).collect{
                    result ->
                _deleteResult.value = result
            }
        }
    }

    fun resetInsertState() {
        _insertResult.value = Resource.Empty
    }

    fun resetUpdateState() {
        _updateResult.value = Resource.Empty
    }

    fun resetDeleteState() {
        _deleteResult.value = Resource.Empty
    }

}