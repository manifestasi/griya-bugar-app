package com.griya.griyabugar.ui.screen.cms.paket

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.components.Button.ButtonBorder
import com.griya.griyabugar.ui.components.Field.DropDownField
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.cmspaket.HargaField
import com.griya.griyabugar.ui.components.cmspaket.InputFotoField
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.screen.cms.paket.tambahpaket.MultiSelectCheckboxList
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun EditPaketScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            AppBarWithBackButton(title = "Edit Paket") {
                // Aksi kembali
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
                ContenSection(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ContenSection(modifier: Modifier = Modifier) {
    var namapaket by rememberSaveable { mutableStateOf("") }
    var diskon by rememberSaveable { mutableStateOf("") }
    var hargaAwal by rememberSaveable { mutableStateOf("") }
    var harga by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedKategori by rememberSaveable { mutableStateOf("") }
    val kategoriOptions = listOf("PROMO", "REGULER")
    var isDisabled by rememberSaveable { mutableStateOf(false) }
    if (
        namapaket.isEmpty()
        ||
        diskon.isEmpty()
        ||
        hargaAwal.isEmpty()
        ||
        harga.isEmpty()
        ||
        selectedKategori.isEmpty()
    ) {
        isDisabled = true
    } else {
        isDisabled = false
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

        if (selectedKategori == "PROMO") {
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
            TextField(
                onChange = {
                    diskon = it
                },
                value = diskon,
                placeHolder = "Masukkan Diskon"
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
            onUploadClick = {},
            value = "",
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
            onUploadClick = {},
            value = "",
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
        MultiSelectCheckboxList()
        Spacer(Modifier.height(16.dp))

        ButtonConfirm(onClick = {}, name = "Simpan", isDisabled = isDisabled)
        Spacer(Modifier.height(10.dp))
        ButtonBorder(onClick = {}, text = "Preview Tampilan", borderColor = GreenColor2)

    }
}

@Preview(showBackground = true)
@Composable
fun EditPaketScreenPreview(modifier: Modifier = Modifier) {
    EditPaketScreen()
}