package com.griya.griyabugar.ui.screen.pemesanan

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.ui.components.Card.CardItemPemesanan
import com.griya.griyabugar.ui.theme.abu
import com.griya.griyabugar.ui.theme.poppins


@Composable
fun SelesaiScreen(items_content:List<ItemPemesananModel>){

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(items_content.size <= 0){
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ){
                Image(
                    painter = painterResource(R.drawable.kosong),
                    contentDescription = "ksng",
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text("Belum Ada Pesanan",
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    color = abu
                )
            }
        }
        /*
        * Scroll View untuk Item Card
        * */
        LazyColumn (
            modifier = Modifier.padding(top = 10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            items(items_content){
                    item ->
                CardItemPemesanan(modifier = Modifier.padding(top = 20.dp).clickable {

                },
                    title = "${item.title}",
                    harga = "${item.harga}",
                    tanggal = "${item.tanggal}",
                    jam = "${item.jam}",
                    items_servis = item.item_servis,
                    isTagged = true,
                    label = "Selesai",
                    jenis_card = "${item.jenis_card}"
                )
            }
        }
    }
}