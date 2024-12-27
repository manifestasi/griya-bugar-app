package com.griya.griyabugar.ui.screen.main.order

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.data.model.LayananModel
import com.griya.griyabugar.data.model.PaketModel
import com.griya.griyabugar.ui.components.Card.CardItemPemesanan
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.layanan.LayananViewModel
import com.griya.griyabugar.ui.screen.paket.PaketViewModel
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.abu
import com.griya.griyabugar.ui.theme.poppins


@Composable
fun SelesaiScreen(
    items_content: List<ItemPemesananModel>,
    paketViewModel: PaketViewModel = hiltViewModel(),
    layananViewModel: LayananViewModel = hiltViewModel(),
    isLoading: Boolean,
    rootNavControll: NavHostController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            // Loading UI
            CircularProgressIndicator(
                color = GreenMain,
                modifier = Modifier.size(50.dp).align(Alignment.CenterHorizontally)
            )
        } else if (items_content.isEmpty()) {
            // Empty state
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.kosong),
                    contentDescription = "kosong",
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    "Belum Ada Pesanan",
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    color = abu
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items_content) { item ->
                    val layananStateMap by layananViewModel.layananState.collectAsState()
                    val paketStateMap by paketViewModel.paketState.collectAsState()
                    val data_paket_state = paketStateMap[item.id_paket] ?: Resource.Empty
                    val arr_layanan = remember(item.id_paket) { mutableStateListOf<String>() }

                    LaunchedEffect(item.id_paket) {
                        if (data_paket_state == Resource.Empty) {
                            paketViewModel.fetchById(item.id_paket)
                        }
                    }

                    when (data_paket_state) {
                        is Resource.Empty -> Text("No data", color = Color.Black)
                        is Resource.Error -> {
                            val error_msg = (data_paket_state as Resource.Error).errorMessage
                            Text("Data Error! | $error_msg", color = Color.Black)
                        }
                        is Resource.Loading -> Text("Loading...", color = Color.Black)
                        is Resource.Success -> {
                            val paket = data_paket_state.data

                            paket.layanan.forEach { layananId ->
                                val data_layanan_state = layananStateMap[layananId]

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

                            CardItemPemesanan(
                                modifier = Modifier.clickable {
                                    /* Klik ke detail pemesanan membawa UUID doc. */
                                    val uuid_data = item.uuid_doc
                                    rootNavControll.navigate(Screen.DetailOrder.createRoute(uuid_data))
                                },
                                title = paket.title,
                                harga = "Rp.${formatNumber(paket.harga)}",
                                tanggal = item.tanggal_servis,
                                jam = item.jam_pemesanan,
                                items_servis = arr_layanan,
                                jenis_card = paket.kategori,
                                isTagged = true,
                                label = "Selesai"
                            )
                        }
                    }
                }
            }
        }
    }
}
