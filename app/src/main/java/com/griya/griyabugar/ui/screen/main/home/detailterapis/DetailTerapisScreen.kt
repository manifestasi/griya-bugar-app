package com.griya.griyabugar.ui.screen.main.home.detailterapis

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.promo.BackButton
import com.griya.griyabugar.ui.components.promo.ServiceRow
import com.griya.griyabugar.ui.screen.main.home.detailpaket.getStartOfCurrentWeek
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.FontOff
import com.griya.griyabugar.ui.theme.GreenColor4
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.poppins
import java.time.LocalDate

@Composable
fun DetailTerapisScreen(
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
            model = R.drawable.img_terapis,
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
            Text(
                text = "Agelica",
                color = TextColorBlack,
                fontSize = 24.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                    text = "Hari Kerja",
                    color = TextColorBlack,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Senin, Selasa, Rabu",
                    color = TextColorBlack,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Jam Kerja",
                    color = TextColorBlack,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier) {

                    Icon(
                        painter = painterResource(R.drawable.ic_time),
                        contentDescription = null,
                        tint = GreenColor4,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Text(
                        text = "10.00 - 20.00 WIB",
                        color = TextColorBlack,
                        fontSize = 20.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(5.dp))


                }

            }
            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}