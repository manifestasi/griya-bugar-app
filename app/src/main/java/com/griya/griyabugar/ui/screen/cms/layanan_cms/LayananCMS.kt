package com.griya.griyabugar.ui.screen.cms.layanan_cms

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.griya.griyabugar.data.model.LayananModel
import com.griya.griyabugar.ui.components.Card.CardLayanan
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.LayananInsertDialog
import com.griya.griyabugar.ui.components.dialog.LayananUpdateDialog
import com.griya.griyabugar.ui.components.dialog.QuestionDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.loading.LoadingAnimation2
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun LayananCMSScreen(
    rootNavController: NavHostController = rememberNavController(),
    layananViewModel: LayananViewModel = hiltViewModel(),
    innerPadding: PaddingValues = PaddingValues(0.dp)
){
    val arr_layanan = remember { mutableStateListOf<LayananModel>() }
    var isLoading by remember { mutableStateOf(true) }
    var showDialogInsert by remember { mutableStateOf(false) }
    var showDialogUpdate by remember { mutableStateOf(false) }
    var showDialogDelete by remember { mutableStateOf(false) }
    var uuid_doc by remember { mutableStateOf("") }
    var name_to_edit by remember { mutableStateOf("") }

    val layanan_state = layananViewModel.getAlllayananState.collectAsState()
    val context = LocalContext.current
    /*
    * untuk dialog insert
    * */

    val insert_state = layananViewModel.insertResult.collectAsState()
    var dialog_state_true by remember { mutableStateOf(false) }
    var dialog_state_failed by remember  { mutableStateOf(false) }

    /*
    * untuk dialog update
    * */
    val update_state = layananViewModel.updateResult.collectAsState()
    var dialog_state_true_update by remember { mutableStateOf(false) }
    var dialog_state_failed_update by remember { mutableStateOf(false) }

    /*
    * untuk delete
    * */
    val delete_state = layananViewModel.deleteResult.collectAsState()


    /*
    * ==============================
    * Fetch Data Layanan
    * =============================
    * */

    LaunchedEffect(Unit) {
        layananViewModel.fetchAll()
    }

    when(val state = layanan_state.value){
        is Resource.Success -> {

            if (arr_layanan.isEmpty()) {
                val data = state.data
                data.forEach { dt ->
                    arr_layanan.add(LayananModel(
                        nama = dt.nama,
                        uuid_doc = dt.uuid_doc
                    ))
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
            arr_layanan.clear()
        }

        else -> {
            /*terserah*/
            arr_layanan.clear()
        }
    }

    /*
    * ======================================
    * Show Dialog
    * =====================================
    * */

    /*
    * Dialog Insert Field
    * */
    if(showDialogInsert){
        LayananInsertDialog(
            title = "Tambah Layanan",
            layananViewModel = layananViewModel,
            onDismiss = {
                showDialogInsert = false
            },
            onBatalClick = {
                showDialogInsert = false
            },
            onSimpanClick = {
                showDialogInsert = false
            }
        )
    }

    /*
    * Dialog Update Field
    * */
    if(showDialogUpdate){
        LayananUpdateDialog(
            title = "Edit Layanan",
            onDismiss = {
                showDialogUpdate = false
            },
            onBatalClick = {
                showDialogUpdate = false
            },
            onSimpanClick = {
                showDialogUpdate = false
            },
            uuid_doc = uuid_doc,
            name_to_edit = name_to_edit
        )
    }

    /*
    * Dialog Delete Confirm
    * */

    LaunchedEffect(delete_state.value) {
        when(val state = delete_state.value){
            is Resource.Success -> {
                Toast.makeText(context, "Berhasil Delete", Toast.LENGTH_SHORT).show()
            }
            is Resource.Error -> {
                Toast.makeText(context, "Gagal Gagal", Toast.LENGTH_SHORT).show()
                layananViewModel.resetDeleteState()
            }

            else -> {
                layananViewModel.resetDeleteState()

            }
        }
    }

    if(showDialogDelete){
        QuestionDialog(
            onDismiss = {
                showDialogDelete = false
            },
            title = "Konfirmasi",
            description = "Apakah Anda yakin untuk menghapus ?",
            btnClickYes = {
                layananViewModel.deleteData(
                    uuid_doc = uuid_doc
                )
                arr_layanan.clear()
                layananViewModel.fetchAll()
                showDialogDelete = false
            },
            btnClickNo = {
                showDialogDelete = false
            }
        )
    }

    /*
    * ==================================================
    *
    * cek state insert
    *
    * =================================================
    * */
    LaunchedEffect(insert_state.value) {
        when(val state = insert_state.value){
            is Resource.Success -> {
                Toast.makeText(context, "Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                dialog_state_true = true
            }
            is Resource.Error -> {
                Toast.makeText(context, "Gagal Update", Toast.LENGTH_SHORT).show()
                dialog_state_failed = true
                layananViewModel.resetInsertState()
            }

            else -> {
                dialog_state_true = false
                layananViewModel.resetInsertState()

            }
        }
    }

    /*
    *
    * Dialog untuk insert
    * */

    if(dialog_state_true){
        SuccessDialog(
            onDismiss = {
                dialog_state_true = false
                arr_layanan.clear()
                layananViewModel.fetchAll()
                layananViewModel.resetInsertState()

            },
            title = "Berhasil",
            description = "Layanan berhasil ditambahkan",
            buttonText = "Selesai",
            buttonOnClick = {
                dialog_state_true = false
                arr_layanan.clear()
                layananViewModel.fetchAll()
                layananViewModel.resetInsertState()

            }
        )

    }

    if(dialog_state_failed){
        ErrorDialog(
            onDismiss = {
                dialog_state_failed = false
                layananViewModel.resetInsertState()

            },
            title = "Gagal",
            description = "Layanan gagal ditambahkan!",
            buttonText = "Coba Lagi",
            buttonOnClick = {
                dialog_state_failed = false
                layananViewModel.resetInsertState()

            }
        )

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
                dialog_state_true_update = true
            }
            is Resource.Error -> {
                Toast.makeText(context, "Gagal Update", Toast.LENGTH_SHORT).show()
                dialog_state_failed_update = true
                layananViewModel.resetUpdateState()

            }

            else -> {
                layananViewModel.resetUpdateState()
            }
        }
    }

    if(dialog_state_true_update){
        SuccessDialog(
            onDismiss = {
                dialog_state_true_update = false
                arr_layanan.clear()
                layananViewModel.fetchAll()
                layananViewModel.resetUpdateState()

            },
            title = "Berhasil",
            description = "Layanan berhasil ditambahkan",
            buttonText = "Selesai",
            buttonOnClick = {
                dialog_state_true_update = false
                arr_layanan.clear()
                layananViewModel.fetchAll()
                layananViewModel.resetUpdateState()

            }
        )
    }

    if(dialog_state_failed_update){
        ErrorDialog (
            onDismiss = {
                dialog_state_failed_update = false
                layananViewModel.resetUpdateState()
            },
            title = "Gagal",
            description = "Layanan gagal ditambahkan!",
            buttonText = "Coba Lagi",
            buttonOnClick = {
                dialog_state_failed_update = false
                layananViewModel.resetUpdateState()

            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)
            .padding(top = 20.dp)
    ) {
        if(isLoading == false){
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(arr_layanan){
                        item->
                    CardLayanan(
                        text = item.nama,
                        onEditClick = {
                            showDialogUpdate = true
                            uuid_doc = item.uuid_doc
                            name_to_edit = item.nama
                        },
                        onDeleteClick = {
                            showDialogDelete = true
                            uuid_doc = item.uuid_doc
                        }
                    )
                }
            }
        } else {
            LoadingAnimation2()
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LayananCMSPreview(){
    GriyaBugarTheme {
        LazyColumn {
            item {
                LayananCMSScreen()

            }
        }
    }
}