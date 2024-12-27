package com.griya.griyabugar.ui.screen.cms.layanan_cms

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
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
import com.griya.griyabugar.ui.components.appbar.AppBar
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.LayananInsertDialog
import com.griya.griyabugar.ui.components.dialog.LayananUpdateDialog
import com.griya.griyabugar.ui.components.dialog.QuestionDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.loading.LoadingAnimation2
import com.griya.griyabugar.ui.screen.layanan.LayananViewModel
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.HijauMuda
import com.griya.griyabugar.ui.theme.HijauTua

@Composable
fun LayananCMSScreen(
    rootNavController: NavHostController = rememberNavController(),
    layananViewModel: LayananViewModel = hiltViewModel(),
    innerPadding: PaddingValues = PaddingValues(0.dp),
    arr_layanan: SnapshotStateList<LayananModel>,
    uuidDoc: (String) -> Unit,
    showDialogUpdate: (Boolean) -> Unit,
    showDialogDelete: (Boolean) -> Unit,
    nameToEdit: (String) -> Unit,
    dialogStateTrueUpdate: (Boolean) -> Unit,
    dialogStateFailedUpdate: (Boolean) -> Unit,
    dialogStateTrue: (Boolean) -> Unit
){
    var isLoading by remember { mutableStateOf(true) }

    val layanan_state = layananViewModel.getAlllayananState.collectAsState()
    val context = LocalContext.current
    /*
    * untuk dialog insert
    * */

    val insert_state = layananViewModel.insertResult.collectAsState()

    /*
    * untuk dialog update
    * */
    val update_state = layananViewModel.updateResult.collectAsState()

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
                dialogStateTrue(true)
            }
            is Resource.Error -> {
                Toast.makeText(context, "Gagal Update", Toast.LENGTH_SHORT).show()
                dialogStateTrue(false)
                layananViewModel.resetInsertState()
            }

            else -> {
                dialogStateTrue(false)
                layananViewModel.resetInsertState()

            }
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
                layananViewModel.resetUpdateState()

            }

            else -> {
                layananViewModel.resetUpdateState()
            }
        }
    }

//    CircularProgressIndicator(
//        color = GreenMain
//    )

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(top = 20.dp)
    ) {
        if(isLoading == false){
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(arr_layanan){
                        item->
                    CardLayanan(
                        text = item.nama,
                        onEditClick = {
                            showDialogUpdate(true)
                            uuidDoc(item.uuid_doc)
                            nameToEdit(item.nama)
                        },
                        onDeleteClick = {
                            showDialogDelete(true)
                            uuidDoc(item.uuid_doc)
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
                LayananCMSScreen(
                    arr_layanan = rememberSaveable { mutableStateListOf<LayananModel>() },
                    showDialogDelete = {},
                    showDialogUpdate = {},
                    uuidDoc = {},
                    nameToEdit = {},
                    dialogStateTrueUpdate = {},
                    dialogStateFailedUpdate = {},
                    dialogStateTrue = {}
                )

            }
        }
    }
}