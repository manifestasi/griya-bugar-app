package com.griya.griyabugar.ui.screen.pemesanan

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.ui.components.Button.BoxButton
import com.griya.griyabugar.ui.components.Button.OptionButton
import com.griya.griyabugar.ui.components.Card.CardItemPemesanan
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.MainColor


@Composable
fun PemesananItemScreen(
    modifier: Modifier=Modifier,
    items_content:List<ItemPemesananModel>
){

    /*
    * state button, default MENUNGGU = 1
    * */
    val state_btn = remember { mutableStateOf(1) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(
                top = 20.dp,
                start = 10.dp
            ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            /*
            * Button Menunggu
            * */
            Button(
                onClick = {
                    state_btn.value = 1
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(if(state_btn.value == 1) MainColor else Color.White) ,
                border = if(state_btn.value != 1) BorderStroke(width = 1.dp, color = Color.Black) else null,
                modifier = modifier
                    .height(50.dp).wrapContentSize()
            ) {
                Text("MENUNGGU",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(listOf(Font(R.font.poppins_regular)))
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            /*
            * Button Selesai
            * */
            Button(
                onClick = {
                    state_btn.value = 2

                },
                border = if(state_btn.value != 2) BorderStroke(width = 1.dp, color = Color.Black) else null,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(if(state_btn.value == 2) MainColor else Color.White),
                modifier = modifier
                    .height(50.dp).wrapContentSize()
            ) {
                Text("SELESAI",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))))
            }

            Spacer(modifier = Modifier.width(10.dp))

            /*
            * Button Batal
            * */
            Button(
                onClick = {
                    state_btn.value = 3

                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(if(state_btn.value == 3) MainColor else Color.White),
                border = if(state_btn.value != 3) BorderStroke(width = 1.dp, color = Color.Black) else null,
                modifier = modifier
                    .height(50.dp).wrapContentSize()
            ) {
                Text("BATAL",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(listOf(Font(R.font.poppins_regular)))
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
                    items_servis = item.item_servis
                )
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemesananScreen(
    modifier:Modifier = Modifier,
    items_content:List<ItemPemesananModel>
){

    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Pemesanan",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(
                            imageVector = Icons.Rounded.ShoppingCart,
                            contentDescription = "icon_appbar",
                            tint = MainColor,
                            modifier = Modifier
                                .size(35.dp)
                                .padding(end = 10.dp)
                        )
                    }
                }
            )
        },
        content = {
            innerPadding ->
            PemesananItemScreen(
                modifier = Modifier.padding(innerPadding),
                items_content = items_content
                )
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PemesananPreview(){
    /*
    * hanya sebagai contoh data, untuk asli buat controler , untuk
    * masing-masing skenario pilihan (state button) button
    * */
    var pemesanan_list = listOf(
        ItemPemesananModel(
            title = "Paket 2 Jam",
            tanggal = "10-09-1965",
            jam = "04.00-05.00",
            item_servis = listOf(
                "SPA",
                "Body Scrum",
                "Tradisional"
            ),
            harga = "Rp.500,000"
        ),
        ItemPemesananModel(
            title = "Paket 3 Jam",
            tanggal = "10-11-1965",
            jam = "04.00-05.00",
            item_servis = listOf(
                "SPA",
                "Body Scrum",
                "Tradisional"
            ),
            harga = "Rp.1,500,000"
        ),

        )
    GriyaBugarTheme {
        PemesananScreen(modifier = Modifier,
            items_content = pemesanan_list )
    }
}