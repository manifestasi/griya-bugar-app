package com.griya.griyabugar.ui.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.griya.griyabugar.ui.components.promo.DiskonBox
import com.griya.griyabugar.ui.components.promo.Rating
import com.griya.griyabugar.ui.components.promo.ServiceRow
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GreenColor4
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.Yellow
import com.griya.griyabugar.ui.theme.poppins


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rootNavController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .verticalScroll(scrollState)
    ) {
        //Header
        HeaderSection(
            rootNavController = rootNavController,
            Modifier.background(
                brush = Brush.linearGradient(
                    listOf(
                        GreenColor1,
                        GreenColor2
                    )
                )
            )
        )
        AddressSection(
            Modifier
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .background(brush = Brush.linearGradient(listOf(GreenColor1, GreenColor2)))
        )
        Spacer(modifier = Modifier.height(25.dp))

        //Promo
        Text(
            text = "Paket Promosi",
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = TextColorBlack,
            modifier = Modifier.padding(start = 16.dp)
        )
        PromoSection(
            rootNavController = rootNavController, modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        //Reguler
        Text(
            text = "Paket Reguler",
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = TextColorBlack,
            modifier = Modifier.padding(start = 16.dp)
        )
        RegulerSection(modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(10.dp))

        //Terapis
        Text(
            text = "Terapis Kami",
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = TextColorBlack,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(5) {
                TerapisSection(modifier = Modifier.fillMaxWidth())
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }


}

@Composable
fun HeaderSection(
    rootNavController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 50.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Column {
            Text(
                text = "Hai, Username",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextColorWhite,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Ayo rawat tubuhmu dengan terapi",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = TextColorWhite,
                modifier = Modifier
            )
        }

        IconButton(
            onClick = {
                rootNavController.navigate(Screen.Notifikasi.route)
            },
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
                .size(40.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                painterResource(R.drawable.ic_notif),
                contentDescription = "Icon",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)

            )
        }
    }
}

@Composable
fun AddressSection(modifier: Modifier) {
    // Konten Alamat
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Icon(
            painterResource(R.drawable.ic_location),
            contentDescription = "Location",
            tint = TextColorWhite,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically)
        )

        Text(
            text = "Jl. Patimura 5, Ruko Patimura Blok 1 Semarang",
            color = TextColorWhite,
            fontFamily = poppins,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painterResource(R.drawable.ic_buka),
            contentDescription = "Location",
            tint = TextColorWhite,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = "Buka",
            color = TextColorWhite,
            fontFamily = poppins,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Composable
fun PromoSection(
    rootNavController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    // Konten Paket Promosi
    val items = listOf(
        "Traditional",
        "Shiatsu",
        "Kerokan",
        "Lulur Badan",
        "Body Scrum"
    )
    Card(
        shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp),
        colors = CardDefaults.elevatedCardColors(BackgroundColor),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                rootNavController.navigate(Screen.DetailPaket.route)
            }
    ) {
        Column(modifier = Modifier) {
            GlideImage(
                model = "https://i.pinimg.com/236x/70/6f/ce/706fceeef69b7ff27985902fc4860612.jpg",
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
                        text = "Paket 2 Jam",
                        color = TextColorBlack,
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Rating(rate = "4.5")
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text(
                        text = "Rp200.000",
                        color = GreenColor4,
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    DiskonBox(text = "Diskon 20%")
                }
                Spacer(modifier = Modifier.height(5.dp))
                ServiceRow(items = items,4)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Composable
fun RegulerSection(modifier: Modifier) {
    // Konten Paket Promosi
    val items = listOf(
        "Traditional",
        "Shiatsu",
        "Kerokan",
        "Lulur Badan",
        "Body Scrum"
    )
    Card(
        shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp),
        colors = CardDefaults.elevatedCardColors(BackgroundColor),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier) {
            GlideImage(
                model = "https://i.pinimg.com/236x/70/6f/ce/706fceeef69b7ff27985902fc4860612.jpg",
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
                        text = "Paket 2 Jam",
                        color = TextColorBlack,
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Rating(rate = "4.5")
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text(
                        text = "Rp200.000",
                        color = GreenColor4,
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
                Spacer(modifier = Modifier.height(5.dp))
               ServiceRow(items = items,4)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Composable
fun TerapisSection(modifier: Modifier) {
    val items = listOf(
        "Traditional",
        "Shiatsu",
        "Kerokan",
        "Lulur Badan",
        "Body Scrum"
    )
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(BackgroundColor),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .width(260.dp)
    ) {
        Column(modifier = Modifier) {
            GlideImage(
                model = "https://i.pinimg.com/236x/70/6f/ce/706fceeef69b7ff27985902fc4860612.jpg",
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
                ServiceRow(items = items,3)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}