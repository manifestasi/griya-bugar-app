package com.griya.griyabugar.ui.screen.detail_pemesanan

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness1
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.Button.BoxButton
import com.griya.griyabugar.ui.components.Button.BoxButtonBorder
import com.griya.griyabugar.ui.components.Button.BoxButtonBorderDP
import com.griya.griyabugar.ui.components.Dialog.RateDialog
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.profile.image.CircleImageProfile
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.abu
import com.griya.griyabugar.ui.theme.orange
import com.griya.griyabugar.ui.theme.poppins

fun copyToClipboard(context: Context, text: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("label", text)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
}

/*
* function untuk mengatur item yang ada pada screen
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemesananItem(modifier: Modifier=Modifier,
                        items_servis:List<String>,
                        context: Context,
                        title : String,
                        harga: String, 
                        diskon: String, 
                        total : String, 
                        id_pesanan : String,
                        tanggal_servis : String,
                        tanggal_pesan:String,
                        status:String,
                        jam:String,
                        isRated:Boolean,
                        rating:Int
                        ){
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    var tag_status = ""
    var showDialog = false

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(top = 120.dp, start = 10.dp, end = 10.dp)
            .fillMaxSize()
    ) {
        Text(title,
            color = Color.Black,
            fontSize = 20.sp,
            fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(5.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            items(items_servis) {
                    item ->
                Row(
                    modifier = Modifier.wrapContentSize(
                        align = Alignment.CenterStart
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Filled.Brightness1,
                        contentDescription = "asdad",
                        modifier = Modifier.size(10.dp),
                        tint = abu
                    )

                    Text("$item",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                        textAlign = TextAlign.Start
                    )
                }

            }
        }
        Spacer(modifier= Modifier.height(10.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){

            when(status){
                "SELESAI" -> Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "cek",
                    tint = if(isRated) GreenMain else abu ,
                    modifier = Modifier.size(30.dp)
                )

                "MENUNGGU" -> Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = "cek",
                    tint = abu,
                    modifier = Modifier.size(30.dp)
                )

                "BATAL" -> Icon(
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = "cek",
                    tint = Color.Red,
                    modifier = Modifier.size(30.dp)
                )
            }


            Spacer(modifier = Modifier.width(10.dp))

            when(status){
                "MENUNGGU" ->
                    tag_status = "Pesanan Menunggu"
                "BATAL" ->
                    tag_status = "Pesanan Batal"
                "SELESAI" ->
                    tag_status = "Pesanan Berhasil"
            }

            Text(tag_status,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text("Pembayaran melalui SPA offline",
            color = Color.DarkGray,
            fontSize = 12.sp,
            fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(1.dp)
                .background(color = Color.Gray)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)

            )
        Spacer(modifier = Modifier.height(10.dp))
        Text("Griya Bugar Pattimura",
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text("Jl. Pattimura 5, Ruko Pattimura Blok 1 Semarang",
            color = Color.DarkGray,
            fontSize = 12.sp,
            fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(1.dp)
                .background(color = Color.Gray)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)

        )
        Spacer(modifier = Modifier.height(15.dp))

        /*
            * Sub paket
            * */
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
            ){

            Text(title,
                color = Color.Black,
                fontSize = 14.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(tanggal_servis,
                color = Color.DarkGray,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        /*
            * Sub harga
            * */
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ){

            Text(harga,
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(jam,
                color = Color.DarkGray,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        /*
            * Sub total
            * */
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ){

            Text("Sub Total",
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(harga,
                color = Color.DarkGray,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        /*
            * Sub diskon
            * */
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ){

            Text("Sub Diskon",
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text("-$diskon",
                color = Color.DarkGray,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        /*
        * dashed line
        * */
        Canvas(
            Modifier
                .fillMaxWidth()
                .height(1.dp)) {

            drawLine(
                color = Color.DarkGray,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect
            )
        }

        Spacer(modifier = Modifier.height(5.dp))
        /*
            * Total
            * */
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ){

            Text("Total",
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
            Text(total,
                color = GreenMain,
                fontSize = 18.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .height(1.dp)
                .background(color = Color.Gray)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)

        )
        Spacer(modifier = Modifier.height(5.dp))
        /*
            * Total
            * */
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ){

            Text("Nama Pesanan",
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically, 
                horizontalArrangement = Arrangement.Start
            ){
                Text(id_pesanan,
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(2.dp))
                Box (
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .background(color = Color.White)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(3.dp)
                        )
                        .clickable {
                            /*
                            * Function untuk salin clipboard
                            * */
                            copyToClipboard(context = context, text = id_pesanan)
                        }
                ){
                    Text("salin",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(3.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        /*
            * Total
            * */
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ){

            Text("Tanggal Memesan",
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(tanggal_pesan,
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if(isRated){
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .background(color = Color.Gray)
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)

            )
            Spacer(modifier = Modifier.height(20.dp))

            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                CircleImageProfile(
                    url = "https://banggaikep.go.id/portal/wp-content/uploads/2024/03/jokowi-1.jpg",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Jokowi",
                        fontFamily = poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                        )
                    Row (
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        for(i in 1..5){
                            Icon(
                                imageVector = Icons.Default.StarRate,
                                contentDescription = "rate",
                                tint = if(i <= rating) orange else abu,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        BoxButtonBorderDP(
                            text = "Edit Penilaian",
                            onClick = {},
                            width = 100.dp,
                            height = 25.dp,
                            fontSize = 12.sp,
                            borderColor = Color.LightGray,
                            fontColor = Color.Black,
                            rounded = 4.dp
                        )
                    }

                }
            }
        }
        

        Spacer(modifier = Modifier.height(30.dp))
        when(status){
            "MENUNGGU" -> BoxButtonBorder(
                onClick = {

                },
                text = "Batal Pesanan",
                borderColor = Color.Red,
                color = Color.White,
                fontColor = Color.Red,
                width = 0.9f,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )

            "SELESAI" -> Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
                ){

                if(isRated){
                    BoxButtonBorder(
                        onClick = {},
                        text = "Pesan Lagi",
//                    color = Color.White,
                        width = 0.9f,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }else{
                    BoxButton(
                        onClick = {
                            RateDialog(
                                onDismissRequest = {},
                                onBatalClick = {},
                                onSimpanClick = {}
                            )
                        },
                        text = "Beri Penilaian",
//                    color = MainColor,
                        width = 0.9f,
                        fontColor = Color.White,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    BoxButtonBorder(
                        onClick = {},
                        text = "Pesan Lagi",
//                    color = Color.White,
                        width = 0.9f,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
                

            }

            "BATAL" ->
                BoxButton(
                    onClick = {},
                    text = "Pesan Lainnya",
//                    color = MainColor,
                    width = 0.9f,
                    fontColor = Color.White,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemesananScreen(
    modifier: Modifier=Modifier,
    items_servis: List<String>,
    title : String,
    harga: String,
    diskon: String,
    total : String,
    id_pesanan : String,
    tanggal_servis : String,
    tanggal_pesan:String,
    status:String,
    jam:String,
    isRated: Boolean,
    rating: Int

){
    val context = LocalContext.current
    Scaffold (
        topBar = {
            AppBarWithBackButton(
                title = "Detail Pemesanan",
                onClickBack = {}
            )

        },
        content = {
                innerPadding ->
            DetailPemesananItem(
                modifier = Modifier.padding(innerPadding),
                items_servis = items_servis,
                context = context,
                title = title,
                harga = harga,
                diskon = diskon,
                total = total,
                id_pesanan = id_pesanan,
                tanggal_servis = tanggal_servis,
                tanggal_pesan = tanggal_pesan,
                status = status,
                jam = jam,
                isRated = isRated,
                rating = rating
            )
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailPemesananPreview(){
    GriyaBugarTheme {
        DetailPemesananScreen(
            modifier = Modifier,
            items_servis = listOf("Spa", "Body Scrum", "Tradisional",
                "Kerokan", "Lulur Badan"
            ),
            title = "Paket 2 Jam",
            harga = "Rp.500,000",
            diskon = "Rp.300,000",
            total = "Rp.200,000",
            id_pesanan = "2323s",
            tanggal_servis = "Kamis, 30 September 1965",
            tanggal_pesan = "Senin, 18/09/65",
            status = "MENUNGGU",
            jam = "10.00-12.00",
            isRated = false,
            rating = 3
            )
    }
}