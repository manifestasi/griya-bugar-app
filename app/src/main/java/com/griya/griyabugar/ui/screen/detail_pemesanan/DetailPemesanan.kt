package com.griya.griyabugar.ui.screen.detail_pemesanan

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.ui.components.Button.BoxButton
import com.griya.griyabugar.ui.components.Button.BoxButtonBorder
import com.griya.griyabugar.ui.components.Button.BoxButtonBorderDP
import com.griya.griyabugar.ui.components.dialog.RateDialog
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.profile.image.CircleImageProfile
import com.griya.griyabugar.ui.screen.cms.layanan_cms.LayananViewModel
import com.griya.griyabugar.ui.screen.main.order.PemesananViewModel
import com.griya.griyabugar.ui.screen.main.order.formatNumber
import com.griya.griyabugar.ui.screen.paket.PaketViewModel
import com.griya.griyabugar.ui.screen.terapis.TerapisViewModel
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
                        rated:Boolean,
                        rating:Int,
                        terapis:String,
                        onBatal:()->Unit,
                        onRate:()->Unit ,
                        onPesanLain:()->Unit,
                        onPesanLagi:()->Unit
                        ) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    var tag_status = ""
    var showDialog = false

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item{
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxSize()
        ) {
            Text(
                title,
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
                modifier = Modifier.height(50.dp)
            ) {
                items(items_servis) { item ->
                    Row(
                        modifier = Modifier.wrapContentSize(
                            align = Alignment.CenterStart
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Brightness1,
                            contentDescription = "asdad",
                            modifier = Modifier.size(10.dp),
                            tint = abu
                        )

                        Text(
                            "$item",
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                            textAlign = TextAlign.Start
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {

                when (status) {
                    "SELESAI" -> Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "cek",
                        tint = if (rated) GreenMain else abu,
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

                when (status) {
                    "MENUNGGU" ->
                        tag_status = "Pesanan Menunggu"

                    "BATAL" ->
                        tag_status = "Pesanan Batal"

                    "SELESAI" ->
                        tag_status = "Pesanan Berhasil"
                }

                Text(
                    tag_status,
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
            Text(
                "Pembayaran melalui SPA offline",
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
            Text(
                "Griya Bugar Pattimura",
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "Jl. Pattimura 5, Ruko Pattimura Blok 1 Semarang",
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    title,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    tanggal_servis,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    harga,
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    jam,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    "Sub Total",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    harga,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    "Sub Diskon",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "-$diskon",
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
                    .height(1.dp)
            ) {

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    "Total",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    total,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    "Nama Pesanan",
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
                ) {
                    Text(
                        id_pesanan,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Box(
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
                    ) {
                        Text(
                            "salin",
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    "Tanggal Memesan",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    tanggal_pesan,
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            when (status) {
                "MENUNGGU" -> BoxButtonBorder(
                    onClick = {
                        onBatal()
                    },
                    text = "Batalkan Pesanan",
                    borderColor = Color.Red,
                    color = Color.White,
                    fontColor = Color.Red,
                    width = 0.9f,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )

                "SELESAI" -> Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    if (rated) {

                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .background(color = Color.Gray)
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterHorizontally)

                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircleImageProfile(
                                url = "https://banggaikep.go.id/portal/wp-content/uploads/2024/03/jokowi-1.jpg",
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Column(
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    terapis,
                                    fontFamily = poppins,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                )
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    for (i in 1..5) {
                                        Icon(
                                            imageVector = Icons.Default.StarRate,
                                            contentDescription = "rate",
                                            tint = if (i <= rating) orange else abu,
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



                        Spacer(modifier = Modifier.height(30.dp))

                        BoxButtonBorder(
                            onClick = {
                                onPesanLagi()
                            },
                            text = "Pesan Lagi",
                            //                    color = Color.White,
                            width = 0.9f,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                    } else {
                        BoxButton(
                            onClick = {
                                onRate()
                            },
                            text = "Beri Penilaian",
                            //                    color = MainColor,
                            width = 0.9f,
                            fontColor = Color.White,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        BoxButtonBorder(
                            onClick = {
                                onPesanLagi()
                            },
                            text = "Pesan Lagi",
                            //                    color = Color.White,
                            width = 0.9f,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                    }


                }

                "BATAL" ->
                    BoxButton(
                        onClick = {
                            onPesanLain()
                        },
                        text = "Pesan Lainnya",
                        //                    color = MainColor,
                        width = 0.9f,
                        fontColor = Color.White,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
            }


        }
    }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemesananScreen(
    modifier: Modifier=Modifier,
    uuid_doc: String?,
    rootNavControll: NavHostController = rememberNavController(),
    detailViewModel:DetailPemesananViewModel = hiltViewModel(),
    paketViewModel: PaketViewModel = hiltViewModel(),
    layananViewModel: LayananViewModel = hiltViewModel(),
    terapisViewModel:TerapisViewModel = hiltViewModel(),
    pemesananViewModel:PemesananViewModel = hiltViewModel()
){
    val context = LocalContext.current

    val title = rememberSaveable { mutableStateOf("") }
    val harga = rememberSaveable { mutableStateOf("") }
    val diskon  = rememberSaveable { mutableStateOf("") }
    val total = rememberSaveable { mutableStateOf("") }
    val id_pesanan = rememberSaveable { mutableStateOf("") }
    val tanggal_servis = rememberSaveable { mutableStateOf("") }
    val tanggal_pesan = rememberSaveable { mutableStateOf("") }
    val status = rememberSaveable { mutableStateOf("") }
    val jam = rememberSaveable { mutableStateOf("") }
    val rated = rememberSaveable { mutableStateOf(false) }
    val rating = rememberSaveable { mutableIntStateOf(0) }
    val terapis = rememberSaveable { mutableStateOf("") }

    val data_detail_stateMap by detailViewModel.detail_data.collectAsState()
    val data_result_state = data_detail_stateMap[uuid_doc] ?: Resource.Empty
    val updateStateBatal by pemesananViewModel.updateResult.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showDialogRate by remember { mutableStateOf(false) }

    LaunchedEffect(uuid_doc) {
        if(data_result_state == Resource.Empty){
            detailViewModel.fetchDetailById(uuid_doc!!)
        }
    }

    when(data_result_state){
        Resource.Empty -> {
            Scaffold (
                topBar = {
                    AppBarWithBackButton(
                        title = "Detail Pemesanan",
                        onClickBack = {
                            rootNavControll.popBackStack()
                        }
                    )

                },
                content = {
                        innerPadding ->
                    Box (
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Data Kosong!",
                            fontFamily = poppins,
                            color = Color.Black,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            )
        }
        is Resource.Error -> {
            rootNavControll.popBackStack()
            Toast.makeText(context, "Tidak dapat memuat data!", Toast.LENGTH_SHORT).show()
        }
        Resource.Loading -> {
            Scaffold (
                topBar = {
                    AppBarWithBackButton(
                        title = "Detail Pemesanan",
                        onClickBack = {}
                    )

                },
                content = {
                        innerPadding ->
                    Box (
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = GreenMain,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            )
        }
        is Resource.Success -> {
            /*
            * ===============================================================
            *              CEK BUTTON STATE
            * ===============================================================
            * */

            /*
            * cek button batal state
            * */
            if(showDialog){
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    title = {
                        Text(text = "Konfirmasi Pembatalan")
                    },
                    text = {
                        Text("Apakah Anda yakin ingin membatalkan pesanan ini?", fontFamily = poppins, color = Color.Black)
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                pemesananViewModel.updateData(
                                    uuid = uuid_doc!!,
                                    field = "status",
                                    new_value = "BATAL"
                                )
                                showDialog = false
                            }
                        ) {
                            Text("Ya", fontFamily = poppins, color = Color.Black)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text("Tidak", fontFamily = poppins, color = Color.Black)
                        }
                    }
                )
            }

            /*
            * cek state batal button
            * */
            when (updateStateBatal) {
                is Resource.Success -> {
                    Toast.makeText(
                        context,
                        "Berhasil membatalkan pesanan",
                        Toast.LENGTH_SHORT
                    ).show()
                    rootNavControll.navigate("main")
                }

                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        "Gagal membatalkan!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    /*
                    * kosong ii ae
                    * */
                }
            }

            /*
            * cek button state onRate
            * */

            if(showDialogRate){
                RateDialog(
                    onDismissRequest = {
                        showDialogRate = false
                    },
                    onBatalClick = {
                        showDialogRate = false
                    },
                    onSimpanClick = {

                    },
                    uuid_doc = uuid_doc
                )
            }


            /*
            * =================================================================================
            *       CEK DATA
            * =================================================================================
            * */

            /*
            *  jika data pemesanan sudah di get
            * */

            /*
            *  layanan state
            * */
            val data_result = data_result_state.data
            Log.d("DATA RESULT", "DATA RESULT : ${data_result}")
            val layananStateMap by layananViewModel.layananState.collectAsState()
            val arr_layanan = remember(data_result.id_paket) { mutableStateListOf<String>() }

            /*
            * paket state
            * */
            val paketStateMap by paketViewModel.paketState.collectAsState()
            val data_paket_state = paketStateMap[data_result.id_paket] ?: Resource.Empty

            /*
            * terapis state
            * */
            val terapisState = terapisViewModel.terapis.collectAsState()

            LaunchedEffect(data_result.id_terapis) {
               terapisViewModel.fetchById(data_result.id_terapis)
            }

            when(val state = terapisState.value){
                is Resource.Success -> {
                    val data_terapis = state.data
                    terapis.value = data_terapis.nama
                }
                else -> {

                }
            }

            tanggal_pesan.value = data_result.tanggal_pemesanan
            tanggal_servis.value = data_result.tanggal_servis
            jam.value = data_result.jam_pemesanan
            rating.value = data_result.rating
            rated.value = data_result.rated
            status.value = data_result.status
            id_pesanan.value = data_result.nomor_pesanan.toString()
            Log.d("IS RATED", "IS RATED : ${rated.value}")
            Log.d("IS RATED RESULT", "IS RATED RESULT : ${data_result.rated}")
            /*
            *  get data paket
            * */
            LaunchedEffect(data_result.id_paket) {
                if (data_paket_state == Resource.Empty) {
                    paketViewModel.fetchById(data_result.id_paket)
                }
            }

            when(data_paket_state){
                is Resource.Success -> {
                    val paket = data_paket_state.data
                    harga.value = "Rp.${formatNumber(paket.harga ?: 0)}"
                    diskon.value = "Rp.${formatNumber(paket.harga ?: (0 * (paket.diskon ?: 0) / 100))}"
                    total.value = "Rp.${formatNumber(((paket.harga ?: (0 - (paket.harga ?: (0 * (paket.diskon ?: 0) / 100))))))}"
                    title.value = paket.title ?: ""


                    paket.layanan.forEach { layananId ->
                        val data_layanan_state = layananStateMap[layananId]

                        /*
                        *  Jika data paket sudah ada,get data layanan
                        * */
                        LaunchedEffect(layananId) {
                            if (data_layanan_state == null) {
                                layananViewModel.fetchById(layananId)
                            }
                        }

                        when (data_layanan_state) {
                            is Resource.Success -> {
                                val layanan = data_layanan_state.data
                                if (!arr_layanan.contains(layanan.nama)) {
                                    arr_layanan.add(layanan.nama)
                                }
                            }
                            else -> { /* Handle other states if needed */ }
                        }
                    }
                }

                else -> {

                }
            }

            Scaffold (
                topBar = {
                    AppBarWithBackButton(
                        title = "Detail Pemesanan",
                        onClickBack = {
                            rootNavControll.popBackStack()
                        }
                    )

                },
                content = {
                        innerPadding ->
                    DetailPemesananItem(
                        modifier = Modifier.padding(innerPadding),
                        items_servis = arr_layanan,
                        context = context,
                        title = title.value,
                        harga = harga.value,
                        diskon = diskon.value,
                        total = total.value,
                        id_pesanan = id_pesanan.value,
                        tanggal_servis = tanggal_servis.value,
                        tanggal_pesan = tanggal_pesan.value,
                        status = status.value,
                        jam = jam.value,
                        rated = rated.value,
                        rating = rating.value,
                        terapis = terapis.value,
                        onBatal = {
                            showDialog = true
                        },
                        onRate = {
                            showDialogRate = true
                        },
                        onPesanLain = {
                            rootNavControll.navigate("main")
                        },
                        onPesanLagi = {
                            /*
                            * sesuaikan ini
                            * */
                            rootNavControll.navigate("main")
                        }
                    )
                }
            )
        }
    }

}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailPemesananPreview(){
    GriyaBugarTheme {
        DetailPemesananScreen(
            modifier = Modifier,
            uuid_doc = "3242sd"
            )
    }
}