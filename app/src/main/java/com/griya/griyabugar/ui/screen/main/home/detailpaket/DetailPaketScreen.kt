package com.griya.griyabugar.ui.screen.main.home.detailpaket

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.ui.components.Button.ButtonGradient
import com.griya.griyabugar.ui.components.dialog.ClockInputDialog
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.home.BackButton
import com.griya.griyabugar.ui.components.home.DiskonBox
import com.griya.griyabugar.ui.components.home.InputJamDialog
import com.griya.griyabugar.ui.components.home.Rating
import com.griya.griyabugar.ui.components.home.ServiceRow
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.screen.SharedViewModel
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.FontOff
import com.griya.griyabugar.ui.theme.GreenColor3
import com.griya.griyabugar.ui.theme.GreenColor6
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.poppins
import com.griya.griyabugar.util.Date
import com.griya.griyabugar.util.Days
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale

@Composable
fun DetailPaketScreen(
    rootNavControll: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    detailPaketViewModel: DetailPaketViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope()
) {

    Box() {
        HeaderSection(
            sharedViewModel = sharedViewModel,
            navController = rootNavControll, modifier = Modifier
        )
        ContentSection(
            modifier = Modifier.padding(top = 240.dp),
            sharedViewModel = sharedViewModel,
            detailPaketViewModel = detailPaketViewModel,
            scope = scope,
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HeaderSection(
    sharedViewModel: SharedViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {

        GlideImage(
            model = sharedViewModel.paketModel2?.fotoDetail ?: "",
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.None,
            loading = placeholder(R.drawable.baseline_person_24),
            failure = placeholder(R.drawable.baseline_person_24)
        )
        BackButton(onClick = { navController.popBackStack() }, Modifier.align(Alignment.TopStart))
    }
}

@SuppressLint("NewApi")
@Composable
private fun ContentSection(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel,
    detailPaketViewModel: DetailPaketViewModel,
    scope: CoroutineScope
) {
    val dataPaket = sharedViewModel.paketModel2
    var showJamDialog by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("10.00") }
//    var selectedDates by remember { mutableStateOf(setOf<LocalDate>()) }
    var selectedDates by rememberSaveable { mutableStateOf<Int>(0) }
    var currentWeekStart by remember { mutableStateOf(getStartOfCurrentWeek()) }

    var isLoading by rememberSaveable { mutableStateOf(false) }

    var isSuccess by rememberSaveable { mutableStateOf(false) }
    var successMessage by rememberSaveable { mutableStateOf("") }

    var isError by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
//    val items = listOf(
//        "Traditional",
//        "Shiatsu",
//        "Kerokan",
//        "Lulur Badan",
//        "Body Scrum"
//    )
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp)) {
            Row(modifier = Modifier) {
                Text(
                    text = dataPaket?.title ?: "",
                    color = TextColorBlack,
                    fontSize = 24.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.weight(1f))
                Rating(rate = "4.5", modifier = Modifier.align(Alignment.CenterVertically))
                Text(
                    text = "(200)",
                    color = TextColorBlack,
                    fontSize = 16.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier) {
                Text(
                    text = "Rp ${dataPaket?.harga ?: ""}",
                    color = GreenColor6,
                    fontSize = 24.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(5.dp))

                if ((dataPaket?.kategori ?: "") == "PROMOSI"){
                    DiskonBox("Diskon ${dataPaket?.diskon ?: ""}%", modifier = Modifier.align(Alignment.CenterVertically))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(modifier = Modifier) {
                Text(
                    text = "Available",
                    color = TextColorBlack,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(5.dp))
                ServiceRow(items = dataPaket?.layanan ?: emptyList(), 3, 16)
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 1.dp, color = FontOff)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier) {
                Text(
                    text = "Pilih Tanggal",
                    color = TextColorBlack,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(5.dp))
                // LazyRow untuk menampilkan tanggal dalam satu minggu
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(Days.getDaysWithDates()) { (day, date) ->
                        DateItem(
                            selectedDates = selectedDates,
                            day = day,
                            date = date,
                            onDateClick = {
                                selectedDates = it
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))


            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier) {
                Text(
                    text = "Pilih Jam",
                    color = TextColorBlack,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                Text(
                    text = "Hanya bisa memesan diantara jam 10.00 - 22.00 ",
                    color = TextColorBlack,
                    fontSize = 12.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(5.dp))

                Row(modifier = Modifier) {
                    Text(
                        text = "$selectedTime WIB",
                        color = TextColorBlack,
                        fontSize = 20.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    IconButton(onClick = {
                        showJamDialog=true
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = null,
                            tint = GreenColor6,
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                ButtonConfirm(onClick = {
                    scope.launch {
                        detailPaketViewModel.addPemesanan(
                            id_paket = dataPaket?.id ?: "",
                            rated = false,
                            jam_pemesanan = selectedTime,
                            tanggal_servis = Date.getCurrentDateFromDay(selectedDates),
                            kategori = dataPaket?.kategori ?: "",
                            paket = dataPaket?.title ?: ""
                        ).collect { event ->
                            when (event){
                                is Resource.Loading -> {
                                    isLoading = true
                                }
                                is Resource.Success -> {
                                    isLoading = false
                                    isSuccess = true
                                    successMessage = event.data
                                }
                                is Resource.Error -> {
                                    isError = true
                                    errorMessage = event.errorMessage
                                }
                                else -> {}
                            }
                        }
                    }
                },
                    name = "Pesan",
                    isLoading = isLoading,
                    isDisabled = isLoading
                )
            }
        }

    }

    // Tampilkan Dialog ketika showJamDialog bernilai true
    if (showJamDialog) {
//        Dialog(onDismissRequest = { showJamDialog = false }) {
//            InputJamDialog(
//                input = selectedTime,
//                modifier = Modifier
//                    .background(BackgroundColor)
//                    .padding(16.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                onSave = {newTime ->
//                    selectedTime = newTime // Update jam yang dipilih
//                    showJamDialog = false // Tutup dialog
//                },
//                onCancel = {
//                    showJamDialog = false
//                }
//            )
//        }
        ClockInputDialog(
            title = "Masukkan Jam Datang",
            btnClickCancel = {
                showJamDialog = false
            },
            btnClickAccept = {
                selectedTime = it
                showJamDialog = false
            },
            onDismiss = {}
        )
    }

    if (isSuccess){
        SuccessDialog(
            onDismiss = {},
            title = "Pesanan berhasil",
            buttonText = "Ok",
            description = successMessage,
            buttonOnClick = {
                isSuccess = false
            }
        )
    }

    if (isError){
        ErrorDialog(
            onDismiss = {
                isError = false
            },
            title = "Pesanan gagal",
            buttonText = "Coba lagi",
            description = "Pesanan Anda Tidak dapat diproses",
            buttonOnClick = {
                isError = false
            }
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun DateItem(
    date: Int,
    day: String,
    selectedDates: Int,
    onDateClick: (Int) -> Unit
) {
//    val items = Days.getDaysWithDates()
//    var checkStates: MutableMap<Int, Boolean> by rememberSaveable(selectedDates) { mutableStateOf(
//        items.associate { (_, date) ->
//            date to (date in selectedDates)
//        }.toMutableMap()
//    ) }

//    val isSelected = checkStates[date] ?: false
    val isSelected = selectedDates == date
    val backgroundColor = if (isSelected) TextColorWhite else FontOff
    val textColor = if (isSelected) GreenColor3 else TextColorBlack

    Column(
        modifier =
        Modifier
            .clickable {
//                checkStates[date] = !isSelected
//                onDateClick(checkStates.filterValues { it }.keys.toList())
                onDateClick(date)
            }
            .then(
                if (isSelected) {
                    Modifier.border(1.dp, GreenColor3, RoundedCornerShape(8.dp))
                } else {
                    Modifier
                }
            )
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(10.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day,
            color = textColor,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = date.toString(),
            color = textColor,
            fontFamily = poppins,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

// Fungsi untuk mendapatkan awal minggu (Senin)
@SuppressLint("NewApi")
fun getStartOfCurrentWeek(): LocalDate {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek.value // 1 = Senin, ..., 7 = Minggu
    return today.minusDays((dayOfWeek - 1).toLong())
}

// Fungsi untuk toggle pemilihan tanggal
fun toggleDateSelection(selectedDates: Set<LocalDate>, date: LocalDate): Set<LocalDate> {
    val newSelection = selectedDates.toMutableSet()
    if (newSelection.contains(date)) {
        newSelection.remove(date) // Jika sudah dipilih, hapus
    } else {
        newSelection.add(date) // Jika belum dipilih, tambahkan
    }
    return newSelection
}