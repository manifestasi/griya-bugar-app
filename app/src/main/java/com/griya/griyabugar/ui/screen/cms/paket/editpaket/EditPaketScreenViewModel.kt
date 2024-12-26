package com.griya.griyabugar.ui.screen.cms.paket.editpaket
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.Layanan
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.model.PaketModelWithLayanan
import com.griya.griyabugar.data.respository.PaketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPaketScreenViewModel @Inject constructor(
    private val paketRepository: PaketRepository
) : ViewModel() {

    private val _layananState = MutableStateFlow<Resource<List<Layanan>>>(Resource.Empty)
    val layananState: StateFlow<Resource<List<Layanan>>> = _layananState

    private val _updateState = MutableStateFlow<UploadResult<Unit>>(UploadResult.Idle)
    val updateState: StateFlow<UploadResult<Unit>> = _updateState

    // StateFlow untuk menyimpan status daftar paket
    private val _paketListState = MutableStateFlow<Resource<List<PaketModel>>>(Resource.Empty)
    val paketListState: StateFlow<Resource<List<PaketModel>>> = _paketListState


    init {
        loadLayanan()
        fetchPaketList()
    }

    fun loadLayanan() {
        viewModelScope.launch {
            _layananState.value = Resource.Loading
            _layananState.value = paketRepository.getLayanan()
        }
    }
    // Fungsi untuk memanggil getPaketList dan mengupdate state
    fun fetchPaketList() {
        viewModelScope.launch {
            _paketListState.value = Resource.Loading // Status Loading
            // Memanggil repository untuk mengambil data
            val result = paketRepository.getPaketList()
            _paketListState.value = result // Menyimpan hasil ke StateFlow
        }
    }
    fun updatePaketWithImages(paketId:String,paket: PaketModel, fotoDepanUri: Uri?, fotoDetailUri: Uri?) {
        viewModelScope.launch {
            paketRepository.updatePaketWithImages(paketId,paket, fotoDepanUri, fotoDetailUri).collect { result ->
                _updateState.value = result
            }
        }
    }

    fun resetUploadState() {
        _updateState.value = UploadResult.Idle
    }
}
