package com.griya.griyabugar.ui.screen.cms.pelanggan_cms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataUser
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.data.model.LayananModel
import com.griya.griyabugar.data.respository.PemesananRepository
import com.griya.griyabugar.data.respository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PelangganViewModel @Inject constructor(
    val userRepo : UserDataRepository,
    val pemesananRepository: PemesananRepository
):ViewModel() {

    /*
    * untuk fetch by id
    * */
    private val _userByIdState = MutableStateFlow<Map<String, Resource<DataUser>>>(emptyMap())
    val userByIdState : StateFlow<Map<String, Resource<DataUser>>> get() = _userByIdState

    /*
    * untuk fetch all
    * */

    private val _getAllPemesananState = MutableStateFlow<Resource<List<ItemPemesananModel>>>(Resource.Loading)
    val getAllPemesananState : StateFlow<Resource<List<ItemPemesananModel>>> get() = _getAllPemesananState


    /*
   * untuk update
   * */
    private val _updateResult = MutableStateFlow<Resource<Boolean>>(Resource.Empty)
    val updateResult : StateFlow<Resource<Boolean>> get() = _updateResult


    /*
    * untuk fetch by id user
    * */
    fun fetchById(uuid:String){
        val curr_state = _userByIdState.value[uuid]
        if(curr_state is Resource.Success || curr_state is Resource.Loading ){
            return
        }

        viewModelScope.launch {
            userRepo.getUserById(uuid).collect{
                result ->
                _userByIdState.value = _userByIdState.value.toMutableMap().apply {
                    this[uuid] = result
                }
            }
        }
    }

    /*
    * fetch all data pemesanan
    * */
    fun fetchAll(){

        viewModelScope.launch {
            pemesananRepository.getPemesananAll().collect{
                    result ->
                _getAllPemesananState.value = result
            }
        }
    }

    /*
    * update pelanggan
    * */


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


    fun resetUpdateState() {
        _updateResult.value = Resource.Empty
    }


}