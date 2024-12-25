package com.griya.griyabugar.ui.screen.cms.paket.previewpaket

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.data.model.PaketModelWithLayanan
import com.griya.griyabugar.ui.components.home.BackButton
import com.griya.griyabugar.ui.components.home.DiskonBox
import com.griya.griyabugar.ui.components.home.ServiceRow
import com.griya.griyabugar.ui.screen.SharedViewModel
import com.griya.griyabugar.ui.screen.cms.paket.ButtonSection
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.FontOff
import com.griya.griyabugar.ui.theme.GreenColor6
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.poppins
import com.griya.griyabugar.util.formatCurrencyIndonesia

@Composable
fun PreviewPaketScreen(
    rootNavControll: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    Box() {
        HeaderSection(
            sharedViewModel = sharedViewModel,
            navController = rootNavControll, modifier = Modifier
        )
        ContentSection(sharedViewModel = sharedViewModel, modifier = Modifier.padding(top = 240.dp))
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HeaderSection(
    sharedViewModel: SharedViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val paket = sharedViewModel.paketModel
    val fotodetail = paket?.fotoDetail

    Box(modifier = modifier.fillMaxWidth()) {

        GlideImage(
            model = fotodetail,
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
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
    val paket = sharedViewModel.paketModel

    val title = paket?.title
    val kategoriPaket = paket?.kategori
    val harga = paket?.harga
    val diskon = paket?.diskon
    val layanan = paket?.layanan

    var kategori = if(kategoriPaket=="PROMOSI") 1 else 0
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp)) {
            Row(modifier = Modifier) {
                if (title != null) {
                    Text(
                        text = title,
                        color = TextColorBlack,
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }
                Spacer(Modifier.width(5.dp))
                VerticalDivider(thickness = 1.dp, color = FontOff, modifier = Modifier.height(30.dp))
                Spacer(Modifier.width(5.dp))
                if (kategoriPaket != null) {
                    Text(
                        text = kategoriPaket,
                        color = TextColorBlack,
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier) {
                Text(
                    text = "Rp$harga",
                    color = GreenColor6,
                    fontSize = 24.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(5.dp))
                if (kategori == 1) {
                    DiskonBox("Diskon $diskon%", modifier = Modifier.align(Alignment.CenterVertically))
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
                if (layanan != null) {
                    ServiceRow(items = layanan, 3, 16)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Preview",
                    color = TextColorBlack,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (paket != null) {
                    if (layanan != null) {
                        PaketItemPreview(paket,layanan)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        }

    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PaketItemPreview(
    paketModel: PaketModel,
    layanan: List<String>,
    modifier: Modifier = Modifier
) {
    var kategori = 0
    val title = paketModel.title
    val kategoriPaket = paketModel.kategori
    val harga = paketModel.harga?.let { formatCurrencyIndonesia(it) }
    val diskon = paketModel.diskon
    val fotodepan = paketModel.fotoDepan

    if (kategoriPaket == "PROMOSI") kategori = 1 else 0

    Card(
        shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp),
        colors = CardDefaults.elevatedCardColors(BackgroundColor),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable {

            }
    ) {
        Column(modifier = Modifier) {
            GlideImage(
                model = fotodepan,
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                contentScale = ContentScale.None,
                loading = placeholder(R.drawable.baseline_person_24),
                failure = placeholder(R.drawable.baseline_person_24)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row {

                        if (title != null) {
                            Text(
                                text = title,
                                color = TextColorBlack,
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                            )
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        if (harga != null) {
                            Text(
                                text = harga,
                                color = GreenColor6,
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        if (kategori == 1) {
                            DiskonBox(text = "Diskon $diskon%")
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    ServiceRow(items = paketModel.layanan, 4)
                }
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewPaketScreenPreview(modifier: Modifier = Modifier) {
    PreviewPaketScreen()
}