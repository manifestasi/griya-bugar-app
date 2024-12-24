package com.griya.griyabugar.ui.screen.cms.terapis.previewterapis

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.griya.griyabugar.R
import com.griya.griyabugar.data.model.DataService
import com.griya.griyabugar.ui.components.promo.BackButton
import com.griya.griyabugar.ui.components.promo.ServiceRow
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.SharedViewModel
import com.griya.griyabugar.ui.screen.main.home.detailpaket.getStartOfCurrentWeek
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.FontOff
import com.griya.griyabugar.ui.theme.GreenColor6
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.poppins
import java.time.LocalDate

@Composable
fun PreviewTerapisScreen(
    rootNavController: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
){
    Box() {
        HeaderSection(
            navController = rootNavController, modifier = Modifier,
            sharedViewModel = sharedViewModel
        )
        ContentSection(
            modifier = Modifier.padding(
                top = 240.dp
            ),
            sharedViewModel = sharedViewModel
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HeaderSection(
    navController: NavHostController = rememberNavController(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel
) {
    val fotoDetail = sharedViewModel.fotoDetailTerapis

    Box(modifier = modifier.fillMaxWidth()) {

        GlideImage(
            model = fotoDetail,
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
    sharedViewModel: SharedViewModel
) {
    val hari = sharedViewModel.hari.joinToString(", ")

    val service = sharedViewModel.layanan.map { it.nama }
    val namaTerapis = sharedViewModel.namaTerapis
    val jamDatang = sharedViewModel.jamDatang
    val jamPulang = sharedViewModel.jamPulang
    val fotoDepan = sharedViewModel.fotoDepanTerapis

    var selectedDates by remember { mutableStateOf(setOf<LocalDate>()) }
    var currentWeekStart by remember { mutableStateOf(getStartOfCurrentWeek()) }
    val items = listOf(
        "Traditional",
        "Shiatsu",
        "Kerokan",
        "Lulur Badan",
        "Body Scrum"
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(BackgroundColor)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp)) {
            Text(
                text = namaTerapis,
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
                ServiceRow(items = service, 3, 16)
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
                    text = hari,
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
                        tint = GreenColor6,
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Text(
                        text = "$jamDatang - $jamPulang WIB",
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

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Preview",
                style = TextStyle(
                    fontFamily = poppins,
                    fontSize = 20.sp
                )
            )

            Spacer(Modifier.height(16.dp))

            TerapisSection(
                service = service,
                fotoDepan = fotoDepan
            )

            Spacer(Modifier.height(102.dp))

        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TerapisSection(
    service: List<String>,
    fotoDepan: String
) {
//    val items = listOf(
//        "Traditional",
//        "Shiatsu",
//        "Kerokan",
//        "Lulur Badan",
//        "Body Scrum"
//    )

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(BackgroundColor),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .width(281.dp)
    ) {
        Column(modifier = Modifier) {
            GlideImage(
                model = fotoDepan,
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                contentScale = ContentScale.None,
                loading = placeholder(R.drawable.baseline_person_24),
                failure = placeholder(R.drawable.baseline_person_24)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Text(
                        text = "Angelica",
                        color = TextColorBlack,
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painterResource(R.drawable.ic_time),
                        contentDescription = "Location",
                        tint = TextColorBlack,
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "10.00 - 12.00",
                        color = TextColorBlack,
                        fontSize = 12.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                ServiceRow(items = service,3)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTerapis(){
    PreviewTerapisScreen()
}
