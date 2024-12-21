package com.griya.griyabugar.ui.screen.main.home.detailpaket

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.Button.ButtonGradient
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.home.BackButton
import com.griya.griyabugar.ui.components.home.DiskonBox
import com.griya.griyabugar.ui.components.home.InputJamDialog
import com.griya.griyabugar.ui.components.home.Rating
import com.griya.griyabugar.ui.components.home.ServiceRow
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.FontOff
import com.griya.griyabugar.ui.theme.GreenColor3
import com.griya.griyabugar.ui.theme.GreenColor4
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.poppins
import java.time.LocalDate
import java.util.Locale

@Composable
fun DetailPaketScreen(
    rootNavControll: NavHostController = rememberNavController()
) {

    Box() {
        HeaderSection(
            navController = rootNavControll, modifier = Modifier
        )
        ContentSection(modifier = Modifier.padding(top = 240.dp))
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HeaderSection(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {

        GlideImage(
            model = R.drawable.img_paket,
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
    modifier: Modifier = Modifier) {
    var showJamDialog by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("10.00") }
    var selectedDates by remember { mutableStateOf(setOf<LocalDate>()) }
    var currentWeekStart by remember { mutableStateOf(getStartOfCurrentWeek()) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val items = listOf(
        "Traditional",
        "Shiatsu",
        "Kerokan",
        "Lulur Badan",
        "Body Scrum"
    )
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp)) {
            Row(modifier = Modifier) {
                Text(
                    text = "Paket 2 Jam",
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
                    text = "Rp200.000",
                    color = GreenColor4,
                    fontSize = 24.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(5.dp))
                DiskonBox("Diskon 50%", modifier = Modifier.align(Alignment.CenterVertically))
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
                ServiceRow(items = items, 3, 16)
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
                    items(7) { dayIndex ->
                        val date = currentWeekStart.plusDays(dayIndex.toLong())
                        DateItem(
                            date = date,
                            isSelected = selectedDates.contains(date),
                            onDateClick = {
                                selectedDates = toggleDateSelection(selectedDates, date)
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
                            tint = GreenColor4,
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                ButtonGradient(onClick = {
                    isError = true
                }, name = "Pesan")
            }
        }

    }

    // Tampilkan Dialog ketika showJamDialog bernilai true
    if (showJamDialog) {
        Dialog(onDismissRequest = { showJamDialog = false }) {
            InputJamDialog(
                input = selectedTime,
                modifier = Modifier
                    .background(BackgroundColor)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp)),
                onSave = {newTime ->
                    selectedTime = newTime // Update jam yang dipilih
                    showJamDialog = false // Tutup dialog
                },
                onCancel = {
                    showJamDialog = false
                }
            )
        }
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
    date: LocalDate,
    isSelected: Boolean,
    onDateClick: () -> Unit
) {
    val backgroundColor = if (isSelected) TextColorWhite else FontOff
    val textColor = if (isSelected) GreenColor3 else TextColorBlack

    Column(
        modifier =
        Modifier
            .width(52.dp)
            .height(58.dp)
            .clickable { onDateClick() }
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
            text = date.dayOfWeek.getDisplayName(
                java.time.format.TextStyle.SHORT,
                Locale.getDefault()
            ),
            color = textColor,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = date.dayOfMonth.toString(),
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