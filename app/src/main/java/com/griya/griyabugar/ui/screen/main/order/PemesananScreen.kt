package com.griya.griyabugar.ui.screen.main.order

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

/*
*  Function untuk membuat Tab Layout Pager
* */
@Composable
fun TabBarPemesanan(
    modifier:Modifier = Modifier,
    items_content_menunggu: List<ItemPemesananModel>,
    items_content_selesai: List<ItemPemesananModel>,
    items_content_batal: List<ItemPemesananModel>,
    isLoading : Boolean,
    rootNavControll: NavHostController
    ){

    var tabs = listOf("MENUNGGU", "SELESAI", "BATAL")
    var pagerState = rememberPagerState(0){3}
    var coroutineScope = rememberCoroutineScope()


    Column (modifier = modifier.fillMaxSize().background(Color.White),
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
                0 -> MenungguScreen(
                    items_content= items_content_menunggu,
                    isLoading = isLoading,
                    rootNavControll = rootNavControll
                    )
                1 -> SelesaiScreen(items_content= items_content_selesai,
                    isLoading= isLoading,
                    rootNavControll = rootNavControll
                    )
                2 -> BatalScreen(items_content= items_content_batal,
                    isLoading = isLoading,
                    rootNavControll = rootNavControll
                    )
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
    rootNavControll: NavHostController = rememberNavController(),
    pemesananViewModel: PemesananViewModel = hiltViewModel()
){
    val items_content_menunggu = remember { mutableStateListOf<ItemPemesananModel>() }
    val items_content_selesai  = remember { mutableStateListOf<ItemPemesananModel>() }
    val items_content_batal = remember { mutableStateListOf<ItemPemesananModel>() }


    /* context from activity */
    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        pemesananViewModel.pemesananData.collect{
            event ->
            when(event){
                is Resource.Loading -> {
                    isLoading = true
                }

                is Resource.Success -> {
                    isLoading = false
                    val data = event.data
                    data?.forEach{
                        item ->
                        when(item.status){
                            "MENUNGGU" -> items_content_menunggu.add(item)
                            "SELESAI" -> items_content_selesai.add(item)
                            "BATAL" -> items_content_batal.add(item)
                        }
                    }

                }

                Resource.Empty -> {
                    isLoading = false
                    items_content_menunggu.clear()
                    items_content_selesai.clear()
                    items_content_batal.clear()
                }
                is Resource.Error -> {
                    isLoading = false
                    Toast.makeText(context, "Terjadi Error saat memuat data! : ${event.errorMessage}", Toast.LENGTH_LONG ).show()
                }
            }
        }
    }

    TabBarPemesanan(
        items_content_menunggu = items_content_menunggu,
        items_content_selesai = items_content_selesai,
        items_content_batal = items_content_batal,
        modifier = modifier,
        isLoading = isLoading,
        rootNavControll = rootNavControll
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PemesananPreview(){
    /*
    * hanya sebagai contoh data, untuk asli buat controler
    * */

    GriyaBugarTheme {
        PemesananScreen(
            modifier = Modifier,
        )

    }
}