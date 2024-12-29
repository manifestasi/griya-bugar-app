package com.griya.griyabugar.ui.screen.cms.paket.editpaket

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.model.PaketModelWithLayanan
import com.griya.griyabugar.ui.components.Button.ButtonBorder
import com.griya.griyabugar.ui.components.Field.DropDownField
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.cmspaket.DiskonField
import com.griya.griyabugar.ui.components.cmspaket.HargaField
import com.griya.griyabugar.ui.components.cmspaket.InputFotoField
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.dialog.UploadDialog
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.SharedViewModel
import com.griya.griyabugar.ui.screen.cms.paket.tambahpaket.MultiSelectCheckboxList
import com.griya.griyabugar.ui.screen.cms.paket.tambahpaket.TambahPaketScreenViewModel
import com.griya.griyabugar.ui.screen.cms.paket.tambahpaket.calculateHargaAkhir
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.poppins

enum class ImageType {
    FOTO_DEPAN, FOTO_DETAIL
}
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditPaketScreen(
    paketId: String = "",
    rootNavController: NavHostController = rememberNavController(),
    viewModel: EditPaketScreenViewModel=hiltViewModel(),
    viewModelTambah: TambahPaketScreenViewModel=hiltViewModel(),
    shareViewModel: SharedViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    modifier: Modifier = Modifier) {

    // Fetch data paket untuk mengisi form
    val paket = (viewModel.paketListState.value as? Resource.Success<List<PaketModel>>)
        ?.data?.find { it.id == paketId }

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            AppBarWithBackButton(title = "Edit Paket") {
                rootNavController.popBackStack()
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues) // Pastikan konten tidak tertutup AppBar
        ) {
            // Konten utama
            item {
                if (paket != null) {
                    ContenSection(
                        viewModel=viewModelTambah,
                        viewModelEdit = viewModel,
                        shareViewModel = shareViewModel,
                        rootNavController = rootNavController,
                        paket = paket,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun ContenSection(
    viewModel: TambahPaketScreenViewModel,
    viewModelEdit: EditPaketScreenViewModel,
    shareViewModel: SharedViewModel = hiltViewModel(),
    rootNavController: NavHostController,
    paket: PaketModel,
    modifier: Modifier = Modifier) {
    var namapaket by rememberSaveable { mutableStateOf(paket.title?:"") }
    var diskon by rememberSaveable { mutableStateOf(paket.diskon.toString()?:"") }
    var hargaAwal by rememberSaveable { mutableStateOf("") }
    var harga by rememberSaveable { mutableStateOf(paket.harga.toString()?:"") }
    var imageUrldepan by rememberSaveable { mutableStateOf<String>(paket.fotoDepan.toString()) }
    var imageUridepan by rememberSaveable { mutableStateOf<Uri?>(null) }
    var imageUrldetail by rememberSaveable { mutableStateOf<String>(paket.fotoDetail.toString()) }
    var imageUridetail by rememberSaveable { mutableStateOf<Uri?>(null) }
    var currentImageType by rememberSaveable { mutableStateOf<ImageType?>(null) }

    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedKategori by rememberSaveable { mutableStateOf(paket.kategori ?: "") }
    val kategoriOptions = listOf("PROMOSI", "REGULER")
    var isDisabled by rememberSaveable { mutableStateOf(false) }
    var isLoadingUpload by rememberSaveable { mutableStateOf(false) }
    var isErrorUpload by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var progress by rememberSaveable { mutableStateOf(0) }
    var isSuccess by rememberSaveable { mutableStateOf(false) }
    // State untuk menyimpan UUID layanan yang dipilih
    val selectedLayanan = remember { mutableStateListOf<String>() }
    val selectedLayananName = remember { mutableStateListOf<String>() }

    val updateState by viewModelEdit.updateState.collectAsState()

    hargaAwal = calculateHargaAwal(harga, diskon).toString()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        when (currentImageType){
            ImageType.FOTO_DEPAN -> {
                if (uri != null){
                    imageUrldepan = uri.toString()
                    imageUridepan = uri
                }
            }
            ImageType.FOTO_DETAIL -> {
                if (uri != null){
                    imageUrldetail = uri.toString()
                    imageUridetail = uri
                }
            }
            else -> {}
        }
    }
    // Validasi input
    isDisabled = when {
        namapaket.isEmpty() ||
                harga.isEmpty() ||
                imageUrldepan.isEmpty() ||
                imageUrldetail.isEmpty() ||
                selectedLayanan.isEmpty()||
                selectedKategori.isEmpty() -> true

        selectedKategori == "PROMOSI" && (diskon.isEmpty() || hargaAwal.isEmpty()) -> true

        else -> false
    }
    Column(modifier = modifier) {
        //Nama Paket
        Text(
            text = "Nama Paket",
            color = TextColorBlack,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
        )
        Spacer(Modifier.height(5.dp))
        TextField(
            onChange = {
                namapaket = it
            },
            value = namapaket,
            placeHolder = "Masukkan Nama Paket"
        )
        Spacer(Modifier.height(16.dp))

        //Kategori Paket
        Text(
            text = "Kategori Paket",
            color = TextColorBlack,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
        )
        Spacer(Modifier.height(5.dp))
        DropDownField(
            label = "Pilih Kategori",
            selectedValue = selectedKategori,
            expanded = expanded,
            onExpandedChange = {
                expanded = !it
            },
            options = kategoriOptions,
            onSelected = {
                selectedKategori = it
            }
        )
        Spacer(Modifier.height(16.dp))

        if (selectedKategori == "PROMOSI") {
            //Harga Awal
            Text(
                text = "Harga Awal",
                color = TextColorBlack,
                fontSize = 16.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
            )
            Spacer(Modifier.height(5.dp))
            HargaField(
                onChange = {
                    hargaAwal = it
                    harga = calculateHargaAkhir(hargaAwal, diskon).toString()
                },
                value = hargaAwal
            )
            Spacer(Modifier.height(16.dp))

            //Diskon
            Text(
                text = "Diskon",
                color = TextColorBlack,
                fontSize = 16.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
            )
            Spacer(Modifier.height(5.dp))
            DiskonField(
                onChange = {
                    diskon = it
                    harga = calculateHargaAkhir(hargaAwal, diskon).toString()
                },
                value = diskon
            )
            Spacer(Modifier.height(16.dp))

            //Harga Akhir
            Text(
                text = "Harga Akhir",
                color = TextColorBlack,
                fontSize = 16.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
            )
            Spacer(Modifier.height(5.dp))
            HargaField(
                enabled = false,
                onChange = {
                    harga = it
                },
                value = harga
            )
            Spacer(Modifier.height(16.dp))
        } else {
            //Harga Akhir
            Text(
                text = "Harga Akhir",
                color = TextColorBlack,
                fontSize = 16.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
            )
            Spacer(Modifier.height(5.dp))
            HargaField(
                onChange = {
                    harga = it
                },
                value = harga
            )
            Spacer(Modifier.height(16.dp))
        }
        
        //Foto Depan
        Text(
            text = "Foto Depan (  Ukuran Foto 380 x 120 px )",
            color = TextColorBlack,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
        )
        Spacer(Modifier.height(5.dp))
        InputFotoField(
            onUploadClick = {
                currentImageType = ImageType.FOTO_DEPAN
                launcher.launch("image/*")
            },
            value = imageUrldepan,
            placeHolder = "Unggah Foto"
        )
        Spacer(Modifier.height(16.dp))

        //Foto Detail
        Text(
            text = "Foto Detail ( Ukuran Foto 412 x 300 px )",
            color = TextColorBlack,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
        )
        Spacer(Modifier.height(5.dp))
        InputFotoField(
            onUploadClick = {
                currentImageType = ImageType.FOTO_DETAIL
                launcher.launch("image/*")
            },
            value = imageUrldetail,
            placeHolder = "Unggah Foto"
        )
        Spacer(Modifier.height(16.dp))

        //Layanan
        Text(
            text = "Layanan",
            color = TextColorBlack,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
        )
        Spacer(Modifier.height(5.dp))
        MultiSelectCheckboxList(
            onSelectionChanged = { selected ->
                selectedLayanan.clear()
                selectedLayanan.addAll(selected)
            }, onSelectionChangedName = { selected ->
                selectedLayananName.clear()
                selectedLayananName.addAll(selected)
            },
            viewModel,
            paketId = paket.id
        )
        Spacer(Modifier.height(16.dp))

        ButtonConfirm(
            onClick = {
           diskon= if(selectedKategori=="PROMOSI") diskon else "0"
                val updatedData = paket.copy(
                    title = namapaket,
                    kategori = selectedKategori,
                    layanan = selectedLayanan.toList(),
                    harga = harga.toInt(),
                    diskon = diskon.toInt(),
                    fotoDepan = imageUrldepan,
                    fotoDetail = imageUrldetail
                )
            paket.id?.let {
                viewModelEdit.updatePaketWithImages(
                    paketId = it,
                    paket = updatedData,
                    fotoDepanUri = imageUridepan,
                    fotoDetailUri = imageUridetail
                )
            }
        }, name = "Simpan", isDisabled = isDisabled)
        Spacer(Modifier.height(10.dp))
        ButtonBorder(onClick = {
            diskon= if(selectedKategori=="PROMOSI") diskon else "0"
            val previewpaket = paket.copy(
                title = namapaket,
                diskon = diskon.toInt(),
                kategori = selectedKategori,
                harga = harga.toInt(),
                layanan = selectedLayananName.toList(),
                fotoDepan = imageUrldepan,
                fotoDetail = imageUrldetail
            )
            shareViewModel.paketModel = previewpaket
            rootNavController.navigate(Screen.PreviewPaket.route)
        }, text = "Preview Tampilan", borderColor = GreenColor2)

        // Status upload
        when (updateState) {
            is UploadResult.Idle -> {
                isLoadingUpload = false
            }

            is UploadResult.Loading -> {
                isLoadingUpload = true
            }

            is UploadResult.Progress -> {
                progress = (updateState as UploadResult.Progress).progress
            }

            is UploadResult.Success -> {
                isLoadingUpload = false
                isSuccess = true
            }

            is UploadResult.Error -> {
                errorMessage = (updateState as UploadResult.Error).errorMessage
                isLoadingUpload = false
                isErrorUpload = true
            }

            is UploadResult.Reschedule -> {
                isLoadingUpload = false
                isErrorUpload = true
                errorMessage = "Rescheduled upload"
            }
        }

        if (isErrorUpload) {
            ErrorDialog(
                onDismiss = {

                },
                title = "Gagal",
                description = errorMessage,
                buttonText = "Coba Lagi",
                buttonOnClick = {
                    isErrorUpload = false
                }
            )
        }

        /* Loading */
        if (isLoadingUpload) {
            UploadDialog(
                onDismiss = {},
                title = "Proses Upload Data",
                description = "Progress $progress %"
            )
        }

        if (isSuccess) {
            SuccessDialog(
                title = "Berhasil",
                description = "Paket berhasil diperbarui",
                buttonText = "Selesai",
                buttonOnClick = {
                    isSuccess = false
                    viewModelEdit.resetUploadState()
                },
                onDismiss = {}
            )
        }

    }
}

// Fungsi untuk menghitung harga awal
fun calculateHargaAwal(hargaAkhir: String, diskon: String): Int {
    val hargaAkhirValue = hargaAkhir.toDoubleOrNull() ?: 0.0
    val diskonValue = diskon.toDoubleOrNull() ?: 0.0
    return if (diskonValue in 0.0..100.0) {
        (hargaAkhirValue / (1 - diskonValue / 100)).toInt() // Konversi ke Int
    } else {
        hargaAkhirValue.toInt() // Harga akhir tetap dalam bentuk Int jika diskon tidak valid
    }
}

@Preview(showBackground = true)
@Composable
fun EditPaketScreenPreview(modifier: Modifier = Modifier) {
    EditPaketScreen()
}