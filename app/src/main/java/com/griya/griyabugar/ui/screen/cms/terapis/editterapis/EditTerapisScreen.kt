package com.griya.griyabugar.ui.screen.cms.terapis.editterapis

import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.DataService
import com.griya.griyabugar.ui.components.Button.ButtonBorderWithTextGradient
import com.griya.griyabugar.ui.components.Field.ClockField
import com.griya.griyabugar.ui.components.Field.ImageField
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.checkbox.DayCheckBox
import com.griya.griyabugar.ui.components.checkbox.ServiceCheckBox
import com.griya.griyabugar.ui.components.dialog.ClockInputDialog
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.loading.LoadingAnimation2
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.SharedViewModel
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

enum class ImageType {
    FOTO_DEPAN, FOTO_DETAIL
}

@Composable
fun EditTerapisScreen(
    id: String = "",
    rootNavController: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    editTerapisViewModel: EditTerapisViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope()
){
    val dataDetailTerapisAndLayanan by editTerapisViewModel.dataDetailTerapisAndLayanan.collectAsState()

    var isFetchingData by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isFetchingData){
            editTerapisViewModel.getDetailTerapisAndLayanan(id)
            isFetchingData = true
        }
    }


    var fotoDepanUrl by rememberSaveable { mutableStateOf("") }
    var fotoDepanUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    var fotoDetailUrl by rememberSaveable { mutableStateOf("") }
    var fotoDetailUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    var currentImageType by rememberSaveable { mutableStateOf<ImageType?>(null) }

    var namaTerapis by rememberSaveable { mutableStateOf("") }

    var showClockDialog by rememberSaveable { mutableStateOf(false) }
    var showClockDialog2 by rememberSaveable { mutableStateOf(false) }

    var jamDatang by rememberSaveable { mutableStateOf("") }
    var jamPulang by rememberSaveable { mutableStateOf("") }

    var isDisabled by rememberSaveable { mutableStateOf(false) }
    val isLoading by editTerapisViewModel.isLoading.collectAsState()

    var isUploadLoading by rememberSaveable { mutableStateOf(false) }

    var isErrorUpload by rememberSaveable { mutableStateOf(false) }
    var errorUploadMessage by rememberSaveable { mutableStateOf("") }

    var isSuccessUpload by rememberSaveable { mutableStateOf(false) }
    var successUploadMessage by rememberSaveable { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        when (currentImageType){
            ImageType.FOTO_DEPAN -> {
                if (uri != null){
                    fotoDepanUrl = uri.toString()
                    fotoDepanUri = uri
                }
            }
            ImageType.FOTO_DETAIL -> {
                if (uri != null){
                    fotoDetailUrl = uri.toString()
                    fotoDetailUri = uri
                }
            }
            else -> {}
        }
    }

    var selectedService: List<String> by rememberSaveable { mutableStateOf(
        emptyList()
    ) }

    var selectedDays: List<String> by rememberSaveable {
        mutableStateOf(
            emptyList()
        )
    }

    var dataLayanan: List<DataService> by rememberSaveable {
        mutableStateOf(emptyList())
    }

    var isInitialized by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(dataDetailTerapisAndLayanan) {
        if (!isInitialized){
            dataDetailTerapisAndLayanan?.let {
                fotoDepanUrl = dataDetailTerapisAndLayanan?.dataTerapis?.foto_depan ?: ""
                fotoDetailUrl = dataDetailTerapisAndLayanan?.dataTerapis?.foto_detail ?: ""
                namaTerapis = dataDetailTerapisAndLayanan?.dataTerapis?.nama ?: ""
                jamDatang = dataDetailTerapisAndLayanan?.dataTerapis?.jam_masuk ?: ""
                jamPulang = dataDetailTerapisAndLayanan?.dataTerapis?.jam_pulang ?: ""
                selectedService = dataDetailTerapisAndLayanan?.dataTerapis?.layanan ?: emptyList()
                selectedDays = dataDetailTerapisAndLayanan?.dataTerapis?.hari_kerja ?: emptyList()

                dataLayanan = dataDetailTerapisAndLayanan?.dataService ?: emptyList()


                Log.d("editTerapisScreen", dataDetailTerapisAndLayanan.toString())
                Log.d("editTerapisScreen", id)

                isInitialized = true
            }
        }
    }

    if(isErrorUpload){
        ErrorDialog(
            onDismiss = {},
            title = "Gagal",
            description = errorUploadMessage,
            buttonText = "Coba Lagi",
            buttonOnClick = {
                isErrorUpload = false
            }
        )
    }

    if (isSuccessUpload){
        SuccessDialog(
            onDismiss = {},
            title = "Berhasil",
            description = successUploadMessage,
            buttonText = "Selesai",
            buttonOnClick = {
                isSuccessUpload = false
                rootNavController.popBackStack()
            }
        )
    }

    if(showClockDialog){
        ClockInputDialog(
            title = "Masukkan Jam Datang",
            onDismiss = {},
            btnClickCancel = {
                showClockDialog = false
            },
            btnClickAccept = {
                jamDatang = it
                showClockDialog = false
            }
        )
    }

    if (showClockDialog2){
        ClockInputDialog(
            title = "Masukkan Jam Pulang",
            onDismiss = {},
            btnClickCancel = {
                showClockDialog2 = false
            },
            btnClickAccept = {
                jamPulang = it
                showClockDialog2 = false
            }
        )
    }

    if (
        namaTerapis.isEmpty()
        ||
        jamDatang.isEmpty()
        ||
        jamPulang.isEmpty()
        ||
        fotoDetailUrl.isEmpty()
        ||
        fotoDepanUrl.isEmpty()
        ||
        selectedService.isEmpty()
        ||
        selectedDays.isEmpty()
    ){
        isDisabled = true
    } else {
        isDisabled = false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarWithBackButton(
                title = "Tambah Terapis",
                onClickBack = {
                    rootNavController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        if (isLoading){
            LoadingAnimation2()
        } else {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .verticalScroll(rememberScrollState())
            ){
                Column(
                    modifier = Modifier.padding(innerPadding)
                        .padding(
                            horizontal = 16.dp
                        )
                ) {

                    Spacer(Modifier.height(32.dp))

                    Text(
                        text = "Nama Terapis",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        onChange = {
                            namaTerapis = it
                        },
                        value = namaTerapis,
                        placeHolder = "Masukkan Nama Terapis"
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "Jam Datang Kerja",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    ClockField(
                        value = jamDatang,
                        placeholder = "Masukan Jam Datang",
                        onClick = {
                            showClockDialog = true
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "Jam Pulang Kerja",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    ClockField(
                        value = jamPulang,
                        placeholder = "Masukan Jam Pulang",
                        onClick = {
                            showClockDialog2 = true
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "Foto Depan (  Ukuran Foto 380 x 120 px )",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    ImageField(
                        value = fotoDepanUrl,
                        placeholder = "Pilih gambar",
                        onClick = {
                            currentImageType = ImageType.FOTO_DEPAN
                            launcher.launch("image/*")
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "Foto Detail ( Ukuran Foto 412 x 300 px )",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    ImageField(
                        value = fotoDetailUrl,
                        placeholder = "Pilih gambar",
                        onClick = {
                            currentImageType = ImageType.FOTO_DETAIL
                            launcher.launch("image/*")
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "Layanan",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    ServiceCheckBox(
                        maxItem = 2,
                        selectedService = selectedService,
                        items = dataLayanan,
                        onSelectionChange = { selected ->
                            selectedService = selected
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "Hari Kerja",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    DayCheckBox(
                        selectedDays = selectedDays,
                        onSelectionChange = { days ->
                            selectedDays = days
                        }
                    )

                    Spacer(Modifier.height(32.dp))

                    ButtonConfirm(
                        name = "Simpan",
                        isLoading = isUploadLoading,
                        isDisabled = isDisabled || isUploadLoading,
                        rounded = 5.dp,
                        onClick = {
                            scope.launch {
                                editTerapisViewModel.updateTerapis(
                                    id = id,
                                    nama = namaTerapis,
                                    jam_pulang = jamPulang,
                                    jam_masuk = jamDatang,
                                    foto_depan = fotoDepanUri,
                                    foto_detail = fotoDetailUri,
                                    layanan = selectedService,
                                    hari_kerja = selectedDays
                                ).collect { event ->
                                    when (event){
                                        is UploadResult.Loading -> {
                                            isUploadLoading = true
                                        }
                                        is UploadResult.Success -> {
                                            isUploadLoading = false
                                            isSuccessUpload = true
                                            successUploadMessage = event.data
                                        }
                                        is UploadResult.Error -> {
                                            isUploadLoading = false
                                            isErrorUpload = true
                                            errorUploadMessage = event.errorMessage
                                        }
                                        else -> {}
                                    }
                                }
                            }
                        },
                    )

                    Spacer(Modifier.height(12.dp))

                    ButtonBorderWithTextGradient(
                        text = "Preview Tampilan",
                        rounded = 5.dp,
                        isDisabled = isDisabled,
                        onClick = {
                            sharedViewModel.layanan = dataLayanan.filter { it.id in selectedService }
                            sharedViewModel.hari = selectedDays
                            sharedViewModel.namaTerapis = namaTerapis
                            sharedViewModel.jamDatang = jamDatang
                            sharedViewModel.jamPulang = jamPulang
                            sharedViewModel.fotoDepanTerapis = fotoDepanUrl
                            sharedViewModel.fotoDetailTerapis = fotoDetailUrl

                            rootNavController.navigate(Screen.PreviewTerapis.route)
                        }
                    )

                    Spacer(Modifier.height(56.dp))

                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditTerapisPreview(){
    GriyaBugarTheme {
        EditTerapisScreen()
    }
}