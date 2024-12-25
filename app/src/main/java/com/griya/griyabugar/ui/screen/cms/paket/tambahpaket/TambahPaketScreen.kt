package com.griya.griyabugar.ui.screen.cms.paket.tambahpaket

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.griya.griyabugar.ui.components.Button.ButtonBorder
import com.griya.griyabugar.ui.components.Field.DropDownField
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.cmspaket.DiskonField
import com.griya.griyabugar.ui.components.cmspaket.HargaField
import com.griya.griyabugar.ui.components.cmspaket.InputFotoField
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.dialog.UploadDialog
import com.griya.griyabugar.ui.components.loading.LoadingAnimation
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.SharedViewModel
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun TambahPaketScreen(
    rootNavController: NavHostController = rememberNavController(),
    viewModel: TambahPaketScreenViewModel = hiltViewModel(),
    shareViewModel: SharedViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppBarWithBackButton(title = "Tambah Paket") {
                rootNavController.navigateUp()
            }
        },
        containerColor = BackgroundColor
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                ContenSection(
                    shareViewModel = shareViewModel,
                    viewModel = viewModel,
                    rootNavController = rootNavController,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ContenSection(
    viewModel: TambahPaketScreenViewModel,
    shareViewModel: SharedViewModel = hiltViewModel(),
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    var namapaket by rememberSaveable { mutableStateOf("") }
    var diskon by rememberSaveable { mutableStateOf("0") }
    var hargaAwal by rememberSaveable { mutableStateOf("0") }
    var harga by rememberSaveable { mutableStateOf("0") }
    var imageUrldepan by rememberSaveable { mutableStateOf<String>("") }
    var imageUridepan by rememberSaveable { mutableStateOf<Uri?>(null) }
    var imageUrldetail by rememberSaveable { mutableStateOf<String>("") }
    var imageUridetail by rememberSaveable { mutableStateOf<Uri?>(null) }

    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedKategori by rememberSaveable { mutableStateOf("") }
    val kategoriOptions = listOf("PROMOSI", "REGULER")
    var isDisabled by rememberSaveable { mutableStateOf(false) }
    var isLoadingUpload by rememberSaveable { mutableStateOf(false) }
    var isErrorUpload by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var progress by rememberSaveable { mutableStateOf(0) }
    var isSuccess by rememberSaveable { mutableStateOf(false) }
    var paket = PaketModel()

    // State untuk menyimpan UUID layanan yang dipilih
    val selectedLayanan = remember { mutableStateListOf<String>() }
    val selectedLayananName = remember { mutableStateListOf<String>() }

    val uploadState by viewModel.uploadState.collectAsState()

    // Membuat launcher untuk membuka galeri
    val launcher1 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUrldepan = uri.toString() // Simpan URI gambar yang dipilih
        }
        imageUridepan = uri
    }

    val launcher2 = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUrldetail = uri.toString() // Simpan URI gambar yang dipilih
        }
        imageUridetail = uri
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
                launcher1.launch("image/*")
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
                launcher2.launch("image/*")
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
            }, viewModel
        )
        Spacer(Modifier.height(16.dp))

        ButtonConfirm(onClick = {
            paket = PaketModel(
                title = namapaket,
                diskon = diskon.toInt(),
                kategori = selectedKategori,
                harga = harga.toInt(),
                layanan = selectedLayanan.toList(),
                fotoDepan = "",
                fotoDetail = ""
            )
            viewModel.addPaketWithImages(paket, imageUridepan, imageUridetail)
        }, name = "Tambah", isDisabled = isDisabled)
        Spacer(Modifier.height(10.dp))
        ButtonBorder(onClick = {
            paket = PaketModel(
                title = namapaket,
                diskon = diskon.toInt(),
                kategori = selectedKategori,
                harga = harga.toInt(),
                layanan = selectedLayananName.toList(),
                fotoDepan = imageUrldepan,
                fotoDetail = imageUrldetail
            )
            shareViewModel.paketModel = paket
            rootNavController.navigate(Screen.PreviewPaket.route)
        }, text = "Preview Tampilan", borderColor = GreenColor2)

        // Status upload
        when (uploadState) {
            is UploadResult.Idle -> {
                isLoadingUpload = false
            }

            is UploadResult.Loading -> {
                isLoadingUpload = true
            }

            is UploadResult.Progress -> {
                progress = (uploadState as UploadResult.Progress).progress
            }

            is UploadResult.Success -> {
                isLoadingUpload = false
                isSuccess = true
            }

            is UploadResult.Error -> {
                errorMessage = (uploadState as UploadResult.Error).errorMessage
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
                description = "Paket berhasil ditambahkan",
                buttonText = "Selesai",
                buttonOnClick = {
                    isSuccess = false
                    viewModel.resetUploadState()
                },
                onDismiss = {}
            )
        }
    }


}

// Fungsi untuk menghitung harga akhir
fun calculateHargaAkhir(hargaAwal: String, diskon: String): Int {
    val hargaAwalValue = hargaAwal.toDoubleOrNull() ?: 0.0
    val diskonValue = diskon.toDoubleOrNull() ?: 0.0
    return if (diskonValue in 0.0..100.0) {
        (hargaAwalValue * (1 - diskonValue / 100)).toInt() // Konversi ke Int
    } else {
        hargaAwalValue.toInt() // Harga awal tetap dalam bentuk Int jika diskon tidak valid
    }
}

@Composable
fun CustomCheckbox(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }

    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .border(
                    width = 2.dp,
                    color = if (isSelected) GreenColor2 else DisabledColor,
                    shape = RoundedCornerShape(5.dp)
                )
                .background(
                    color = if (isSelected) TextColorWhite else Color.Transparent,
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = TextColorBlack,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
fun MultiSelectCheckboxGridWithoutScroll(modifier: Modifier = Modifier) {
    val items = remember { List(5) { "Traditional ${it + 1}" } } // Daftar item
    val selectedItems = remember { mutableStateListOf<String>() }

    // Membagi daftar menjadi kelompok dua kolom
    val rows = items.chunked(2)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
    ) {
        rows.forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(40.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { item ->
                    CustomCheckbox(
                        isSelected = selectedItems.contains(item),
                        text = item,
                        onClick = {
                            if (selectedItems.contains(item)) {
                                selectedItems.remove(item)
                            } else {
                                selectedItems.add(item)
                            }
                        },
                        modifier = modifier.weight(1f) // Agar ukuran item di dalam Row merata
                    )
                }

                // Jika item kurang dari 2 dalam satu baris, tambahkan spacer
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun MultiSelectCheckboxList(
    onSelectionChanged: (List<String>) -> Unit,
    onSelectionChangedName: (List<String>) -> Unit,
    viewModel: TambahPaketScreenViewModel
) {
    // State untuk menampung layanan dari ViewModel
    val layananState = viewModel.layananState.collectAsState().value

    // State untuk menampung UUID layanan yang dipilih
    val selectedItems = remember { mutableStateListOf<String>() }
    val selectedItemsName = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        viewModel.loadLayanan() // Memuat data layanan saat pertama kali composable dipanggil
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        when (layananState) {
            is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            is Resource.Success -> {
                val layananList = layananState.data

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(200.dp) // Sesuaikan tinggi
                ) {
                    items(layananList) { layanan ->
                        CustomCheckbox(
                            isSelected = selectedItems.contains(layanan.id),
                            text = layanan.nama, // Nama layanan yang ditampilkan
                            onClick = {
                                if (selectedItems.contains(layanan.id) && selectedItemsName.contains(
                                        layanan.nama
                                    )
                                ) {
                                    selectedItems.remove(layanan.id)
                                    selectedItemsName.remove(layanan.nama)
                                } else {
                                    selectedItems.add(layanan.id)
                                    selectedItemsName.add(layanan.nama)

                                    onSelectionChanged(selectedItems)
                                    onSelectionChangedName(selectedItemsName)
                                }
                            }
                        )
                    }
                }
            }

            is Resource.Error -> {
                Text(
                    text = "Gagal memuat layanan: ${layananState.errorMessage}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            is Resource.Empty -> Text(
                "Tidak ada layanan tersedia.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

}

@Composable
fun MultiSelectCheckboxList() {
    val items = remember { List(5) { "Traditional ${it + 1}" } }// Simulasi daftar item
    val selectedItems = remember { mutableStateListOf<String>() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.height(150.dp)
    ) {
        items(items) { item ->
            CustomCheckbox(
                isSelected = selectedItems.contains(item),
                text = item,
                onClick = {
                    if (selectedItems.contains(item)) {
                        selectedItems.remove(item)
                    } else {
                        selectedItems.add(item)
                    }
                }
            )
        }
    }
}

@Composable
fun LayananItem(modifier: Modifier = Modifier) {
    val layananList = listOf("Traditional", "Shiatsu", "Body Scrum", "Kerokan", "Lulur Badan")
    val checkedStates = remember { mutableStateMapOf<String, Boolean>() }

    // Inisialisasi checkedStates
    layananList.forEach {
        if (!checkedStates.containsKey(it)) {
            checkedStates[it] = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Layanan",
            color = TextColorBlack,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 kolom
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(layananList) { layanan ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedStates[layanan] ?: false,
                        onCheckedChange = { isChecked ->
                            checkedStates[layanan] = isChecked
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = GreenColor2,
                            uncheckedColor = DisabledColor
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = layanan,
                        color = TextColorBlack,
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TambahPaketScreenPreview(modifier: Modifier = Modifier) {
    TambahPaketScreen()
}