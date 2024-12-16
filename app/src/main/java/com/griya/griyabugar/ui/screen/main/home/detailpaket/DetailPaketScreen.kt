package com.griya.griyabugar.ui.screen.main.home.detailpaket

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.Button.ButtonGradient
import com.griya.griyabugar.ui.components.promo.DiskonBox
import com.griya.griyabugar.ui.components.promo.Rating
import com.griya.griyabugar.ui.components.promo.ServiceRow
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.FontOff
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
        Box(modifier = Modifier
            .padding(top = 40.dp, start = 16.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(TextColorWhite)
            .size(30.dp)

            .align(Alignment.TopStart)
            .clickable {
                navController.navigateUp()
            })
        {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                tint = TextColorBlack,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
private fun ContentSection(modifier: Modifier = Modifier) {
    var selectedDates by remember { mutableStateOf(setOf<LocalDate>()) }
    var currentWeekStart by remember { mutableStateOf(getStartOfCurrentWeek()) }
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
                        text = "10.00 WIB",
                        color = TextColorBlack,
                        fontSize = 20.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    IconButton(onClick = {}) {
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

                ButtonGradient(onClick = {}, name = "Pesan")
            }
        }

    }
}

@SuppressLint("NewApi")
@Composable
fun DateItem(
    date: LocalDate,
    isSelected: Boolean,
    onDateClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF4CAF50) else Color.LightGray
    val textColor = if (isSelected) Color.White else Color.Black

    Column(
        modifier = Modifier
            .width(60.dp)
            .clickable { onDateClick() }
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.dayOfWeek.getDisplayName(
                java.time.format.TextStyle.SHORT,
                Locale.getDefault()
            ),
            color = textColor,
            fontSize = 12.sp
        )
        Text(
            text = date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
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