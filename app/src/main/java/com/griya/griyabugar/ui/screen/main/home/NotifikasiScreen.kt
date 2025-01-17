package com.griya.griyabugar.ui.screen.main.home

import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.DataNotificationModel
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton2
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor6
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.poppins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun NotifikasiScreen(
    rootNavControll: NavHostController = rememberNavController(),
    notifikasiViewModel: NotifikasiViewModel = hiltViewModel()
) {
    var count = 10
    val dataNotification by notifikasiViewModel.dataNotification.collectAsState()

    LaunchedEffect(Unit) {
        notifikasiViewModel.fetchGetAllNotification()
    }

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(BackgroundColor)
        ) {

            AppBarWithBackButton(
                title = "Notifikasi",
                onClickBack = {
                    rootNavControll.popBackStack()
                }
            )

            when (dataNotification) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val notificationData = (dataNotification as Resource.Success<List<DataNotificationModel>>).data

                    if (notificationData.isEmpty()) {
                        NotifikasiKosong(modifier = Modifier.fillMaxSize())
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(notificationData, key = { it.timeStamp ?: it.uid }) { notifikasi ->
                                NotifikasiItem(
                                    modifier = Modifier,
                                    dataNotification = notifikasi,
                                    notifikasiViewModel = notifikasiViewModel
                                )
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    Text(
                        text = "Terjadi error: ${(dataNotification as Resource.Error).errorMessage}",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {
                    NotifikasiKosong(modifier = Modifier.fillMaxSize())
                }
            }


//            if (count == 0) {
//                NotifikasiKosong(modifier = Modifier)
//            } else {
//                LazyColumn(
//                    contentPadding = PaddingValues(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    items(count) {
//                        NotifikasiItem(modifier = Modifier)
//                    }
//                }
//            }
        }
    }
}

@Composable
private fun NotifikasiItem(
    modifier: Modifier = Modifier,
    dataNotification: DataNotificationModel,
    notifikasiViewModel: NotifikasiViewModel,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val context = LocalContext.current
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
                    text = dataNotification.date,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = TextColorBlack,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = dataNotification.title,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = GreenColor6,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = dataNotification.text,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = TextColorBlack,
                    modifier = Modifier
                )
            }
            IconButton(
                onClick = {
                    scope.launch {
                        notifikasiViewModel.deleteNotification(
                            dataNotification.uid
                        )
                            .catch {  }
                            .collect { event ->
                                when (event){
                                    is Resource.Loading -> {
                                        Toast.makeText(context, "Proses delete", Toast.LENGTH_SHORT).show()
                                    }
                                    is Resource.Success -> {
                                        Toast.makeText(context, event.data, Toast.LENGTH_SHORT).show()
                                        notifikasiViewModel.fetchGetAllNotification()
                                    }
                                    else -> {}
                                }
                            }
                    }
                },
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