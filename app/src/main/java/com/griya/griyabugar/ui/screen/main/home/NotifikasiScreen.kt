package com.griya.griyabugar.ui.screen.main.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton2
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor4
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun NotifikasiScreen(
    rootNavControll: NavHostController = rememberNavController()
) {
    var count = 10
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(BackgroundColor)
        ) {
            AppBarWithBackButton2(
                title = "Notifikasi",
                onClickBack = {
                    rootNavControll.popBackStack()
                }
            )
            if (count == 0) {
                NotifikasiKosong(modifier = Modifier)
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(count) {
                        NotifikasiItem(modifier = Modifier)
                    }
                }
            }
        }
    }
}

@Composable
private fun NotifikasiItem(modifier: Modifier = Modifier) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = BackgroundColor
        ),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, DisabledColor),
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "2 Januari 2024",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = TextColorBlack,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Pesanan berhasil dibuat",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = GreenColor4,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Reguler paket 2 jam pukul 10.00 WIB",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = TextColorBlack,
                    modifier = Modifier
                )
            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    painterResource(R.drawable.ic_delete),
                    contentDescription = "delete",
                    tint = TextColorBlack,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun NotifikasiKosong(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Icon(
                painter = painterResource(R.drawable.ic_bell),
                contentDescription = null,
                tint = DisabledColor,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)

            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Belum Ada Notifikasi",
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = DisabledColor,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }


    }
}