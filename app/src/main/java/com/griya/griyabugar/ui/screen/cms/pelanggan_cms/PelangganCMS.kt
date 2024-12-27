package com.griya.griyabugar.ui.screen.cms.pelanggan_cms

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.PelangganModel
import com.griya.griyabugar.ui.components.Card.CardPelanggan
import com.griya.griyabugar.ui.components.appbar.AppBar
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.loading.LoadingAnimation2
import com.griya.griyabugar.ui.screen.paket.PaketViewModel
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.orange

@Composable
fun PelangganCMSScreen(
    rootNavController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues = PaddingValues(0.dp),
    pelangganViewModel: PelangganViewModel = hiltViewModel(),
    paketViewModel: PaketViewModel = hiltViewModel(),
    dialogStateTrueUpdate: (Boolean) -> Unit,
    arr_pelanggan: SnapshotStateList<PelangganModel>,
    showDialogUpdate: (Boolean) -> Unit,
    nameToEdit: (String) -> Unit,
    uuidDoc: (String) -> Unit,
    dialogStateFailedUpdate: (Boolean) -> Unit,
){
    var isLoading by remember { mutableStateOf(true) }

    val pelanggan_pemesanan_state = pelangganViewModel.getAllPemesananState.collectAsState()

    val context = LocalContext.current

    /*
    * untuk dialog update
    * */
    val update_state = pelangganViewModel.updateResult.collectAsState()



    /*
    * ==============================
    * Fetch Data Pelanggan
    * =============================
    * */

    LaunchedEffect(Unit) {
        pelangganViewModel.fetchAll()
    }

    when(val state = pelanggan_pemesanan_state.value){
        is Resource.Success -> {

            if (arr_pelanggan.isEmpty()) {
                val pemesananData = state.data


                if(arr_pelanggan.isEmpty()){
                    pemesananData.forEach { dt ->
                        val data_pelanggan_stataMap by pelangganViewModel.userByIdState.collectAsState()
                        val data_pelanggan_result = data_pelanggan_stataMap[dt.id_user] ?: Resource.Empty

                        val paketStateMap by paketViewModel.paketState.collectAsState()
                        val data_paket_state = paketStateMap[dt.id_paket] ?: Resource.Empty

                        LaunchedEffect(dt.id_paket) {
                            paketViewModel.fetchById(dt.id_paket)
                        }

                        LaunchedEffect(dt.id_user) {
                            pelangganViewModel.fetchById(dt.id_user)

                        }

                        LaunchedEffect(dt.id_paket) {
                            if (data_paket_state == Resource.Empty) {
                                paketViewModel.fetchById(dt.id_paket)
                            }
                        }

                        when(data_pelanggan_result){
                            is Resource.Success -> {
//                                val pelanggan_data = data_pelanggan_result.data
                                val pelanggan_data = data_pelanggan_result.data


                                when(data_paket_state){
                                    is Resource.Success -> {
                                        val paket_data = data_paket_state.data
                                        arr_pelanggan.add(
                                            PelangganModel(
                                                uuid_doc = dt.uuid_doc,
                                                nama = pelanggan_data.nama!!,
                                                status = dt.status,
                                                kategori = paket_data.kategori,
                                                title = paket_data.title,
                                                tanggal = dt.tanggal_servis,
                                                url_img = if(pelanggan_data.nama == "") null else pelanggan_data.role!!
                                            )
                                        )

                                        Log.d("Data paket ki", "DATA PAKET KI : ${paket_data}")
                                    }

                                    is Resource.Loading ->{
                                        isLoading = true
                                    }

                                    is Resource.Empty -> {
                                        arr_pelanggan.clear()
                                        isLoading = true

                                    }
                                    is Resource.Error -> {
                                        Toast.makeText(context, "Error get paket! | ${data_paket_state.errorMessage}", Toast.LENGTH_SHORT).show()

                                    }
                                }

                            }

                            is Resource.Loading -> {
                                isLoading = true
                            }

                            Resource.Empty -> {
//                                Toast.makeText(context, "data pelanggan empty", Toast.LENGTH_SHORT).show()

                            }
                            is Resource.Error -> {
                                Toast.makeText(context, "Error get pelanggan! | ${data_pelanggan_result.errorMessage}", Toast.LENGTH_SHORT).show()
                            }
                        }


                    }
                }
            }
            isLoading = false
        }

        is Resource.Loading -> {
            isLoading = true
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = GreenMain
                )
            }
//            arr_pelanggan.clear()
        }

        else -> {
            /*terserah*/
//            arr_pelanggan.clear()
        }
    }


    /*
      * ==================================================
      *
      * cek state update
      *
      * =================================================
      * */

    LaunchedEffect(update_state.value) {
        when(val state = update_state.value){
            is Resource.Success -> {
                Toast.makeText(context, "Berhasil Update", Toast.LENGTH_SHORT).show()
                dialogStateTrueUpdate(true)

            }
            is Resource.Error -> {
                Toast.makeText(context, "Gagal Update", Toast.LENGTH_SHORT).show()
                dialogStateFailedUpdate(true)
                pelangganViewModel.resetUpdateState()

            }

            else -> {
                pelangganViewModel.resetUpdateState()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)
            .padding(top = 20.dp)
    ) {
        if(isLoading){
            LoadingAnimation2()
        }else{
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(arr_pelanggan){
                        item->

                    val colorStatus = remember { mutableStateOf<Color>(GreenMain) }

                    when(item.status){
                        "MENUGGU" -> {
                            colorStatus.value = orange
                        }

                        "BATAL" -> {
                            colorStatus.value = Color.Red
                        }

                        "SELESAI" -> {
                            colorStatus.value = GreenMain
                        }
                    }

                    CardPelanggan(
                        nama = item.nama,
                        kategori = item.kategori,
                        tanggal = item.tanggal,
                        paket = item.title,
                        status = item.status,
                        color_status = colorStatus.value,
                        url_img = item.url_img,
                        onEditClick = {
                            uuidDoc(item.uuid_doc)
                            nameToEdit(item.status)
                            showDialogUpdate(true)
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LayananCMSPreview(){
    GriyaBugarTheme {
        LazyColumn {
            item {
                PelangganCMSScreen(
                    arr_pelanggan = remember { mutableStateListOf() },
                    dialogStateTrueUpdate = {},
                    showDialogUpdate = {},
                    nameToEdit = {},
                    uuidDoc = {},
                    dialogStateFailedUpdate = {}
                )

            }
        }
    }
}