package com.griya.griyabugar.ui.screen.cms.paket

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.data.model.PaketModelWithLayanan
import com.griya.griyabugar.data.model.PaketModelWithLayanan2
import com.griya.griyabugar.ui.components.Button.ButtonDelete
import com.griya.griyabugar.ui.components.Button.ButtonEdit
import com.griya.griyabugar.ui.components.appbar.AppBarWithDrawer
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.QuestionDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.dialog.UploadDialog
import com.griya.griyabugar.ui.components.home.DiskonBox
import com.griya.griyabugar.ui.components.home.ServiceRow
import com.griya.griyabugar.ui.components.loading.LoadingAnimation2
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GreenColor6
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.poppins
import com.griya.griyabugar.util.formatCurrencyIndonesia

@Composable
fun PaketScreen(
    innerPadding: PaddingValues,
    navController: NavHostController = rememberNavController(),
    viewModel: PaketScreenViewModel = hiltViewModel()
) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    val paketState by viewModel.paketState.collectAsState()
    val deleteState by viewModel.deleteState.collectAsState()
    var isError by rememberSaveable { mutableStateOf(false) }
    var isLoadingUpload by rememberSaveable { mutableStateOf(false) }
    var isErrorUpload by rememberSaveable { mutableStateOf(false) }
    var progress by rememberSaveable { mutableStateOf(0) }
    var isSuccess by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var showQuestionDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var paketId by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchPaketWithLayananNames()
    }
    Scaffold(
        topBar = {
            AppBarWithDrawer("Paket") { }
        },
        containerColor = BackgroundColor,
        content = { paddingValues ->

            when (paketState) {
                is Resource.Loading -> {
                   LoadingAnimation2()
                }

                is Resource.Success -> {
                    val paketList =
                        (paketState as Resource.Success<List<PaketModelWithLayanan2>>).data
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(paketList) { paket ->
                            PaketItem(onDeleteClick = { Id->
                                paketId = Id
                                showQuestionDeleteDialog = true
                                                      },navController, paket)
                            if (showQuestionDeleteDialog){
                                QuestionDialog(
                                    onDismiss = {},
                                    title = "Apakah kamu yakin?",
                                    description = "Apakah kamu yakin ingin menghapusnya?",
                                    btnClickNo = {
                                        showQuestionDeleteDialog = false
                                    },
                                    btnClickYes = {
                                        val fotodepan = paket.fotoDepan?:""
                                        val fotodetail = paket.fotoDetail?:""
                                        viewModel.deletePaket(
                                            paketId,
                                            fotodepan,
                                            fotodetail
                                        )
                                        showQuestionDeleteDialog = false
                                    }
                                )
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    isError = true
                    errorMessage = (paketState as Resource.Error).errorMessage
                }

                Resource.Empty -> {

                }
            }

            when(deleteState){
                is UploadResult.Idle -> {
                    isLoadingUpload = false
                }

                is UploadResult.Loading -> {
                    isLoadingUpload = true
                }

                is UploadResult.Progress -> {
                    progress = (deleteState as UploadResult.Progress).progress
                }

                is UploadResult.Success -> {
                    isLoadingUpload = false
                    isSuccess = true
                }

                is UploadResult.Error -> {
                    errorMessage = (deleteState as UploadResult.Error).errorMessage
                    isLoadingUpload = false
                    isErrorUpload = true
                }

                is UploadResult.Reschedule -> {
                    isLoadingUpload = false
                    isErrorUpload = true
                    errorMessage = "Rescheduled upload"
                }
            }

            if (isError) {
                ErrorDialog(
                    onDismiss = {},
                    buttonText = "Oke",
                    buttonOnClick = {
                        isError = false
                    },
                    title = "Oops",
                    description = errorMessage
                )
            }

            if (isErrorUpload) {
                ErrorDialog(
                    onDismiss = {

                    },
                    title = "Gagal",
                    description = errorMessage,
                    buttonText = "Coba Lagi",
                    buttonOnClick = {
                        isErrorUpload = false
                    }
                )
            }

            /* Loading */
            if (isLoadingUpload) {
                UploadDialog(
                    onDismiss = {},
                    title = "Proses Delete Data",
                    description = "Progress $progress %"
                )
            }

            if (isSuccess) {
                SuccessDialog (
                    title = "Berhasil",
                    description = "Paket berhasil di hapus",
                    buttonText = "Selesai",
                    buttonOnClick = {
                        isSuccess = false
                        viewModel.resetDeleteState()
                        viewModel.fetchPaketWithLayananNames()
                    },
                    onDismiss = {}
                )
            }
        }


    )
}

@Composable
fun GradientFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(GreenColor1, GreenColor2),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Brush.linearGradient(gradientColors))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PaketItem(
    onDeleteClick: (String) -> Unit,
    rootNavController: NavHostController = rememberNavController(),
    paketModel: PaketModelWithLayanan2,
    preview: Boolean = false,
    modifier: Modifier = Modifier
) {
    var kategori = 0
    val title = paketModel.title
    val kategoriPaket = paketModel.kategori
    val harga = paketModel.harga?.let { formatCurrencyIndonesia(it) }
    val diskon = paketModel.diskon
    val fotodepan = paketModel.fotoDepan

    if (kategoriPaket == "PROMOSI") kategori = 1 else 0

    Card(
        shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp),
        colors = CardDefaults.elevatedCardColors(BackgroundColor),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier) {
            GlideImage(
                model = fotodepan ?: R.drawable.img_paket,
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                contentScale = ContentScale.None,
                loading = placeholder(R.drawable.baseline_person_24),
                failure = placeholder(R.drawable.baseline_person_24)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row {

                        if (title != null) {
                            Text(
                                text = title,
                                color = TextColorBlack,
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                            )
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        if (harga != null) {
                            Text(
                                text = harga,
                                color = GreenColor6,
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        if (kategori == 1) {
                            DiskonBox(text = "Diskon $diskon%")
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    ServiceRow(items = paketModel.layananNames, 4)
                }
                if (!preview) {
                    ButtonSection(
                        onEditClick = {
                            if (paketModel.id != null) {
                                rootNavController.navigate(Screen.EditPaket.createRoute(paketModel.id))
                            }
                        },
                        onDeleteClick = {
                            paketModel.id?.let { onDeleteClick(it) }
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun ButtonSection(
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        ButtonEdit(onClick = {
            onEditClick()
        })
        Spacer(modifier = Modifier.width(10.dp))
        ButtonDelete(onClick = {
            onDeleteClick()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PaketScreenPreview() {
    GriyaBugarTheme {
        PaketScreen(innerPadding = PaddingValues(0.dp))
    }
}

