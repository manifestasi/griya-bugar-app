package com.griya.griyabugar.ui.screen.main.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataTerapis
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.model.PaketModelWithLayanan
import com.griya.griyabugar.ui.components.home.DiskonBox
import com.griya.griyabugar.ui.components.home.Rating
import com.griya.griyabugar.ui.components.home.ServiceRow
import com.griya.griyabugar.ui.components.loading.LoadingAnimation2
import com.griya.griyabugar.ui.components.loading.LoadingAnimation3
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.SharedViewModel
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GreenColor6
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.poppins
import com.griya.griyabugar.util.isOpen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rootNavController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollState = rememberScrollState()

    val dataTerapis by homeViewModel.dataTerapis.collectAsState()
    val dataPaket by homeViewModel.dataPaket.collectAsState()

    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .verticalScroll(scrollState)
    ) {
        AddressSection(
            Modifier
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .background(brush = Brush.linearGradient(listOf(GreenColor1, GreenColor2)))
        )
        Spacer(modifier = Modifier.height(25.dp))

        when (dataPaket){
            is Resource.Loading -> {
                //Promo
                Text(
                    text = "Paket Promosi",
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = TextColorBlack,
                    modifier = Modifier.padding(start = 16.dp)
                )
                LoadingAnimation3()

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

                LoadingAnimation3()

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
            }
            is Resource.Success -> {

                val dataResult = (dataPaket as Resource.Success<List<PaketModelWithLayanan>>).data
                val promoResult = dataResult.filter {
                    it.kategori == "PROMOSI"
                }
                val regulerResult = dataResult.filter {
                    it.kategori == "REGULER"
                }

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
                    sharedViewModel = sharedViewModel,
                    dataPromo = promoResult,
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
                RegulerSection(
                    sharedViewModel = sharedViewModel,
                    dataReguler = regulerResult,
                    rootNavController = rootNavController,
                    modifier = Modifier.padding(16.dp)
                )
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
            }
            else -> {

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when(dataTerapis){
            is Resource.Loading -> {
                Box(Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                ){
                    LoadingAnimation3()
                }
            }
            is Resource.Success -> {
                val dataResult = (dataTerapis as Resource.Success<List<DataTerapis>>).data
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(dataResult, key = {it.timemilis}) { terapis ->
                        TerapisSection(
                            sharedViewModel = sharedViewModel,
                            terapis = terapis,
                            rootNavController = rootNavController,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

    }


}

@Composable
fun AddressSection(modifier: Modifier) {
    // Konten Alamat
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Row {
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
        }
//        Spacer(modifier = Modifier.weight(1f))
        Row {
            Icon(
                painterResource(R.drawable.ic_buka),
                contentDescription = "Location",
                tint = if(isOpen())TextColorWhite else DisabledColor,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = if(isOpen())"Buka" else "Tutup",
                color = if(isOpen())TextColorWhite else DisabledColor,
                fontFamily = poppins,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Composable
fun PromoSection(
    sharedViewModel: SharedViewModel,
    dataPromo: List<PaketModelWithLayanan>,
    rootNavController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    // Konten Paket Promosi
//    val items = listOf(
//        "Traditional",
//        "Shiatsu",
//        "Kerokan",
//        "Lulur Badan",
//        "Body Scrum"
//    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        dataPromo.forEach {
            Card(
                shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp),
                colors = CardDefaults.elevatedCardColors(BackgroundColor),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        sharedViewModel.paketModel = PaketModel(
                            title = it.title,
                            harga = it.harga,
                            layanan = it.layananNames,
                            fotoDepan = it.fotoDepan,
                            fotoDetail = it.fotoDetail,
                            diskon = it.diskon,
                            kategori = it.kategori
                        )
                        rootNavController.navigate(Screen.DetailPaket.route)
                    }
            ) {
                Column(modifier = Modifier) {
                    GlideImage(
                        model = it.fotoDepan,
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
                                text = it.title ?: "",
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
                                text = "Rp.${it.harga ?: ""}",
                                color = GreenColor6,
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            DiskonBox(text = "Diskon ${it.diskon}%")
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        ServiceRow(items = it.layananNames,4)
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RegulerSection(
    modifier: Modifier,
    sharedViewModel: SharedViewModel,
    dataReguler: List<PaketModelWithLayanan>,
    rootNavController: NavHostController,
) {
    // Konten Paket Promosi
//    val items = listOf(
//        "Traditional",
//        "Shiatsu",
//        "Kerokan",
//        "Lulur Badan",
//        "Body Scrum"
//    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        dataReguler.forEach {
            Card(
                shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp),
                colors = CardDefaults.elevatedCardColors(BackgroundColor),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        sharedViewModel.paketModel = PaketModel(
                            title = it.title,
                            harga = it.harga,
                            layanan = it.layananNames,
                            fotoDepan = it.fotoDepan,
                            fotoDetail = it.fotoDetail,
                            diskon = it.diskon,
                            kategori = it.kategori
                        )

                        rootNavController.navigate(Screen.DetailPaket.route)
                    }
            ) {
                Column(modifier = Modifier) {
                    GlideImage(
                        model = it.fotoDepan ?: "",
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
                                text = it.title ?: "",
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
                                text = "Rp.${it.harga ?: ""}",
                                color = GreenColor6,
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        ServiceRow(items = it.layananNames,4)
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Composable
fun TerapisSection(
    sharedViewModel: SharedViewModel,
    terapis: DataTerapis,
    rootNavController: NavHostController = rememberNavController(),
    modifier: Modifier
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
            .width(260.dp)
            .clickable {
                sharedViewModel.namaTerapis = terapis.nama
                sharedViewModel.layananName = terapis.layanan
                sharedViewModel.fotoDepanTerapis = terapis.foto_depan
                sharedViewModel.jamPulang = terapis.jam_pulang
                sharedViewModel.jamDatang = terapis.jam_masuk
                sharedViewModel.hari = terapis.hari_kerja
                sharedViewModel.fotoDetailTerapis = terapis.foto_detail
                rootNavController.navigate(Screen.DetailTerapis.route)
            }
    ) {
        Column(modifier = Modifier) {
            GlideImage(
                model = terapis.foto_depan,
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
                        text = terapis.nama,
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
                        text = "${terapis.jam_masuk} - ${terapis.jam_pulang}",
                        color = TextColorBlack,
                        fontSize = 12.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                ServiceRow(items = terapis.layanan,3)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    GriyaBugarTheme {
        HomeScreen()
    }
}