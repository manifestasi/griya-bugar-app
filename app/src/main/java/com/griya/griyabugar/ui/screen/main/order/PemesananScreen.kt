package com.griya.griyabugar.ui.screen.main.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins
import kotlinx.coroutines.launch

/*
*  Function untuk membuat Tab Layout Pager
* */
@Composable
fun TabBarPemesanan(
    modifier:Modifier = Modifier,
    items_content_menunggu: List<ItemPemesananModel>,
    items_content_selesai: List<ItemPemesananModel>,
    items_content_batal: List<ItemPemesananModel>
    
    ){

    var tabs = listOf("MENUNGGU", "SELESAI", "BATAL")
    var pagerState = rememberPagerState(0){3}
    var coroutineScope = rememberCoroutineScope()


    Column (modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        TabRow(
            modifier = Modifier,
            contentColor = GreenMain,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 4.dp,
                    color = GreenMain
                )
            },
            selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    selectedContentColor = GreenMain,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = tabs.size,
            modifier = Modifier.padding(10.dp)
        ) {
            page ->
            when (page){
                0 -> MenungguScreen(items_content= items_content_menunggu)
                1 -> SelesaiScreen(items_content= items_content_selesai)
                2 -> BatalScreen(items_content= items_content_batal)
            }
        }
    }
}

/*
* Gunakan function ini sebagai screen pemesanan.
* */

@Composable
fun PemesananScreen(
    modifier:Modifier = Modifier,
    items_content_menunggu:List<ItemPemesananModel> = listOf(),
    items_content_selesai:List<ItemPemesananModel> = listOf(),
    items_content_batal:List<ItemPemesananModel> = listOf()
){
    TabBarPemesanan(
        items_content_menunggu = items_content_menunggu,
        items_content_selesai = items_content_selesai,
        items_content_batal = items_content_batal
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PemesananPreview(){
    /*
    * hanya sebagai contoh data, untuk asli buat controler
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
            harga = "Rp.500,000",
            jenis_card = "PROMOSI"
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
            harga = "Rp.1,500,000",
            jenis_card = "REGULER"

        ),

        )
    GriyaBugarTheme {
        PemesananScreen(
            modifier = Modifier,
            items_content_menunggu = pemesanan_list,
            items_content_selesai = listOf(),
            items_content_batal = listOf(),
        )

    }
}