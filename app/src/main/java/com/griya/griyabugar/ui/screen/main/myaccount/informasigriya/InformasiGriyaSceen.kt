package com.griya.griyabugar.ui.screen.main.myaccount.informasigriya

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun InformasiGriyaScreen(
    onNavigateBack: () -> Unit,
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarWithBackButton(
                title = "Informasi Griya Bugar",
                onClickBack = onNavigateBack
            )
        }
    ) { innerPaddding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddding)
                .background(Color.White)
                .padding(
                    horizontal = 16.dp
                )
        ) {

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Alamat Griya Bugar Patimura",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Jl Patimura 5, Ruko Patimura Blok 1 Semarang",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )

            Spacer(
                Modifier.height(20.dp)
            )

            Text(
                text = "Hari dan Jam Buka",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Setiap Hari, Jam 10.00 - 23.00 WIB",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )

            Spacer(
                Modifier.height(20.dp)
            )

            Text(
                text = "Nomor Telepon",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "0882005002819",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InformasiGriyaPreview(){
    GriyaBugarTheme {
        InformasiGriyaScreen(
            onNavigateBack = {}
        )
    }
}