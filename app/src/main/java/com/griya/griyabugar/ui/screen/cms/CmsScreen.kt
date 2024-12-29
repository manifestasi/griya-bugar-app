package com.griya.griyabugar.ui.screen.cms

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.model.LayananModel
import com.griya.griyabugar.data.model.NavDrawerItem
import com.griya.griyabugar.data.model.PelangganModel
import com.griya.griyabugar.ui.components.appbar.AppBarWithDrawer
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.LayananInsertDialog
import com.griya.griyabugar.ui.components.dialog.LayananUpdateDialog
import com.griya.griyabugar.ui.components.dialog.QuestionDialog
import com.griya.griyabugar.ui.components.dialog.StatusPelangganDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.loading.LoadingAnimation
import com.griya.griyabugar.ui.components.statusbar.UpdateStatusBarColor
import com.griya.griyabugar.ui.navigation.NavDrawScreen
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.cms.layanan_cms.LayananCMSScreen
import com.griya.griyabugar.ui.screen.cms.paket.PaketScreen
import com.griya.griyabugar.ui.screen.cms.pelanggan_cms.PelangganCMSScreen
import com.griya.griyabugar.ui.screen.cms.pelanggan_cms.PelangganViewModel
import com.griya.griyabugar.ui.screen.cms.terapis.TerapisScreen
import com.griya.griyabugar.ui.screen.cms.layanan_cms.LayananViewModel
import com.griya.griyabugar.ui.screen.cms.paket.GradientFloatingActionButton
import com.griya.griyabugar.ui.screen.paket.PaketViewModel
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.HijauMuda
import com.griya.griyabugar.ui.theme.HijauTua
import com.griya.griyabugar.ui.theme.RedColor1
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.poppins
import com.griya.griyabugar.util.finishAffinity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CmsScreen(
    rootNavController: NavHostController = rememberNavController(),
    layananViewModel: LayananViewModel = hiltViewModel(),
    pelangganViewModel: PelangganViewModel = hiltViewModel(),
    paketViewModel: PaketViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
    cmsViewModel:CmsScreenViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by rememberSaveable { mutableStateOf(NavDrawScreen.Pelanggan.route) }

    var showDialogInsert by rememberSaveable { mutableStateOf(false) }
    var showDialogUpdate by rememberSaveable { mutableStateOf(false) }
    var showDialogUpdate2 by rememberSaveable { mutableStateOf(false) }
    var showDialogDelete by rememberSaveable { mutableStateOf(false) }
    var uuid_doc by rememberSaveable { mutableStateOf("") }
    var name_to_edit by rememberSaveable { mutableStateOf("") }
    val arr_layanan = remember { mutableStateListOf<LayananModel>() }
    val arr_pelanggan = remember { mutableStateListOf<PelangganModel>() }

    var dialog_state_true_update by remember { mutableStateOf(false) }
    var dialog_state_failed_update by remember { mutableStateOf(false) }
    var dialog_state_true_update2 by remember { mutableStateOf(false) }
    var dialog_state_failed_update2 by remember { mutableStateOf(false) }

    var dialog_state_true by remember { mutableStateOf(false) }
    var dialog_state_failed by remember  { mutableStateOf(false) }

    var isLogout by remember { mutableStateOf(false) }


    /*
    * ======================================
    * Show Dialog
    * =====================================
    * */

    /* Untuk Logout */

    if(isLogout){
        QuestionDialog(
            onDismiss = {},
            title = "Keluar Akun",
            description = "Apakah anda yakin keluar akun?",
            btnClickYes = {
                scope.launch {
                    cmsViewModel.signout()
                    cmsViewModel.logout_state.collect { event ->
                        when (event){
                            is Resource.Loading -> {
                                Toast.makeText(context, "Loading proses logout", Toast.LENGTH_LONG).show()
                            }

                            is Resource.Success -> {

                                /* Keluar dari aplikasi */
                                finishAffinity(context)
                            }

                            is Resource.Error -> {
                                Toast.makeText(context, event.errorMessage, Toast.LENGTH_LONG).show()
                            }

                            else -> {
                            }
                        }
                    }
                }
                isLogout = false
            },
            btnClickNo = {
                isLogout = false
            }
        )
    }


    if(dialog_state_failed_update2){
        ErrorDialog(
            onDismiss = {
                dialog_state_failed_update2 = false
                pelangganViewModel.resetUpdateState()
            },
            title = "Gagal",
            description = "Layanan gagal ditambahkan!",
            buttonText = "Coba Lagi",
            buttonOnClick = {
                dialog_state_failed_update2 = false
                pelangganViewModel.resetUpdateState()

            }
        )
    }

    /*
    * Dialog Update Field
    * */
    if(showDialogUpdate2){

        StatusPelangganDialog(
            title = "Edit Status",
            onDismiss = {
                showDialogUpdate2 = false
            },
            onBatalClick = {
                showDialogUpdate2 = false
            },
            onSimpanClick = {
                showDialogUpdate2 = false
            },
            uuid_doc = uuid_doc,
            name_to_edit = name_to_edit,
            pelangganViewModel = pelangganViewModel
        )
    }

    if(dialog_state_true_update2){
        SuccessDialog(
            onDismiss = {
                dialog_state_true_update2 = false
                arr_pelanggan.clear()
                pelangganViewModel.fetchAll()
                pelangganViewModel.resetUpdateState()

            },
            title = "Berhasil",
            description = "Layanan berhasil ditambahkan",
            buttonText = "Selesai",
            buttonOnClick = {
                dialog_state_true_update2 = false
                arr_pelanggan.clear()
                pelangganViewModel.fetchAll()
                pelangganViewModel.resetUpdateState()

            }
        )
    }

    /*
    * Dialog Insert Field
    * */
    if(showDialogInsert){
        LayananInsertDialog(
            title = "Tambah Layanan",
            layananViewModel = layananViewModel,
            onDismiss = {
                showDialogInsert = false
            },
            onBatalClick = {
                showDialogInsert = false
            },
            onSimpanClick = {
                showDialogInsert = false
            }
        )
    }

    /*
    * Dialog Update Field
    * */
    if(showDialogUpdate){
        LayananUpdateDialog(
            title = "Edit Layanan",
            onDismiss = {
                showDialogUpdate = false
            },
            onBatalClick = {
                showDialogUpdate = false
            },
            onSimpanClick = {
                showDialogUpdate = false
            },
            uuid_doc = uuid_doc,
            name_to_edit = name_to_edit
        )
    }

    if(showDialogDelete){
        QuestionDialog(
            onDismiss = {
                showDialogDelete = false
            },
            title = "Konfirmasi",
            description = "Apakah Anda yakin untuk menghapus ?",
            btnClickYes = {
                layananViewModel.deleteData(
                    uuid_doc = uuid_doc
                )
                arr_layanan.clear()
                layananViewModel.fetchAll()
                showDialogDelete = false
            },
            btnClickNo = {
                showDialogDelete = false
            }
        )
    }

    /*
    *
    * Dialog untuk insert
    * */

    if(dialog_state_true){
        SuccessDialog(
            onDismiss = {
                dialog_state_true = false
                arr_layanan.clear()
                layananViewModel.fetchAll()
                layananViewModel.resetInsertState()

            },
            title = "Berhasil",
            description = "Layanan berhasil ditambahkan",
            buttonText = "Selesai",
            buttonOnClick = {
                dialog_state_true = false
                arr_layanan.clear()
                layananViewModel.fetchAll()
                layananViewModel.resetInsertState()

            }
        )

    }

    if(dialog_state_failed){
        ErrorDialog(
            onDismiss = {
                dialog_state_failed = false
                layananViewModel.resetInsertState()

            },
            title = "Gagal",
            description = "Layanan gagal ditambahkan!",
            buttonText = "Coba Lagi",
            buttonOnClick = {
                dialog_state_failed = false
                layananViewModel.resetInsertState()

            }
        )

    }

    if(dialog_state_true_update){
        SuccessDialog(
            onDismiss = {
                dialog_state_true_update = false
                arr_layanan.clear()
                layananViewModel.fetchAll()
                layananViewModel.resetUpdateState()

            },
            title = "Berhasil",
            description = "Layanan berhasil ditambahkan",
            buttonText = "Selesai",
            buttonOnClick = {
                dialog_state_true_update = false
                arr_layanan.clear()
                layananViewModel.fetchAll()
                layananViewModel.resetUpdateState()

            }
        )
    }

    if(dialog_state_failed_update){
        ErrorDialog(
            onDismiss = {
                dialog_state_failed_update = false
                layananViewModel.resetUpdateState()
            },
            title = "Gagal",
            description = "Layanan gagal ditambahkan!",
            buttonText = "Coba Lagi",
            buttonOnClick = {
                dialog_state_failed_update = false
                layananViewModel.resetUpdateState()

            }
        )
    }

    UpdateStatusBarColor(
        darkIcons = false
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectedItem = selectedItem,
                rootNavController = rootNavController,
                onItemClick = { item ->
                    selectedItem = item
                    scope.launch {
                        drawerState.close()
                    }
                },
                cmsViewModel = cmsViewModel,
                onLogout = {
                    isLogout = it
                }
            )
        }
    ) {

        Scaffold(
            topBar = {
                AppBarWithDrawer(
                    title = if (selectedItem == NavDrawScreen.Layanan.route){
                        "Layanan"
                    } else if (selectedItem == NavDrawScreen.Pelanggan.route){
                        "Pelanggan"
                    } else if (selectedItem == NavDrawScreen.Terapis.route){
                        "Terapis"
                    } else if (selectedItem == NavDrawScreen.Paket.route){
                        "Paket"
                    } else {
                        ""
                    },
                    onNavigationMenu = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            floatingActionButton = {
                if (selectedItem == NavDrawScreen.Terapis.route){
                    Image(
                        modifier = Modifier
                            .offset(y = (-40).dp)
                            .size(64.dp)
                            .clickable {
                            rootNavController.navigate(Screen.AddTerapis.route)
                        },
                        painter = painterResource(R.drawable.img_fab),
                        contentDescription = "fab"
                    )
                } else if (selectedItem == NavDrawScreen.Layanan.route){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(60.dp)
                            .background(
                                brush = Brush.horizontalGradient(listOf(HijauMuda, HijauTua)),
                                shape = CircleShape
                            )
                    ) {
                        FloatingActionButton(
                            onClick = {
                                showDialogInsert = true
                            },
                            containerColor = Color.Transparent,
                            elevation = FloatingActionButtonDefaults.elevation(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }else if(selectedItem == NavDrawScreen.Paket.route) {
                    GradientFloatingActionButton(
                        onClick = { rootNavController.navigate(Screen.TambahPaket.route) },
                        gradientColors = listOf(
                            HijauMuda,
                            HijauTua
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah Paket",
                            tint = TextColorWhite
                        )
                    }
                }

            },
        ) { innerPadding ->

            if (selectedItem == NavDrawScreen.Pelanggan.route){
                PelangganCMSScreen(
                    innerPadding = innerPadding,
                    rootNavController = rootNavController,
                    showDialogUpdate = {
                        showDialogUpdate2 = it
                    },
                    nameToEdit = {
                        name_to_edit = it
                    },
                    uuidDoc = {
                        uuid_doc = it
                    },
                    arr_pelanggan = arr_pelanggan,
                    dialogStateTrueUpdate = {
                        dialog_state_true_update2 = it
                    },
                    dialogStateFailedUpdate = {
                        dialog_state_failed_update2 = it
                    },
                    pelangganViewModel = pelangganViewModel,
                    paketViewModel = paketViewModel
                )
            } else if (selectedItem == NavDrawScreen.Paket.route){
                PaketScreen(innerPadding,rootNavController)
            } else if (selectedItem == NavDrawScreen.Layanan.route){
                LayananCMSScreen(
                    layananViewModel = layananViewModel,
                    innerPadding = innerPadding,
                    rootNavController = rootNavController,
                    arr_layanan = arr_layanan,
                    uuidDoc = {
                        uuid_doc = it
                    },
                    showDialogUpdate = {
                        showDialogUpdate = it
                    },
                    showDialogDelete = {
                        showDialogDelete = it
                    },
                    nameToEdit = {
                        name_to_edit = it
                    },
                    dialogStateFailedUpdate = {
                        dialog_state_failed_update = it
                    },
                    dialogStateTrue = {
                        dialog_state_true = it
                    },
                    dialogStateTrueUpdate = {
                        dialog_state_true_update = it
                    }
                )
            } else if (selectedItem == NavDrawScreen.Terapis.route){
                TerapisScreen(
                    innerPadding = innerPadding,
                    rootNavController = rootNavController,
                )
            }

        }

    }
}

@Composable
fun DrawerContent(
    selectedItem: String,
    rootNavController: NavHostController,
    cmsViewModel:CmsScreenViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit,
    onLogout: (Boolean) -> Unit
){

    val menuItems = listOf(
        NavDrawerItem(
            label = "Pelanggan",
            icon = R.drawable.ic_pelanggan,
            route = NavDrawScreen.Pelanggan.route
        ),
        NavDrawerItem(
            label = "Paket",
            icon = R.drawable.ic_order,
            route = NavDrawScreen.Paket.route
        ),
        NavDrawerItem(
            label = "Layanan",
            icon = R.drawable.ic_layanan,
            route = NavDrawScreen.Layanan.route
        ),
        NavDrawerItem(
            label = "Terapis",
            icon = R.drawable.ic_terapis,
            route = NavDrawScreen.Terapis.route
        ),
        NavDrawerItem(
            label = "Keluar",
            icon = R.drawable.ic_exit,
            route = ""
        ),
    )

    Column (
        modifier = Modifier
            .width(207.dp)
            .fillMaxHeight()
            .background(Color.White)
            .padding(
                horizontal = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(26.dp))

        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            painter = painterResource(R.drawable.logo_drawer),
            contentDescription = "Logo",
        )

        Spacer(Modifier.height(39.dp))

        menuItems.forEachIndexed { index, item ->

            if (selectedItem == item.route){
                Box(
                    modifier = Modifier
                        .width(191.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(
                            5.dp
                        ))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    GreenColor1,
                                    GreenColor2
                                )
                            )
                        )
                ) {

                    Row (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.label,
                            tint = Color.White
                        )

                        Spacer(Modifier.width(24.dp))

                        Text(
                            text = item.label,
                            style = TextStyle(
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        )
                    }

                }
            } else {
                Box(
                    modifier = Modifier
                        .width(191.dp)
                        .height(54.dp)
                        .clip(RoundedCornerShape(
                            5.dp
                        ))
                        .background(
                            color = Color.Transparent
                        )
                        .clickable {
                            if ((menuItems.size-1) == index){
                                onLogout(true)
                            } else {
                                onItemClick(item.route)
                            }
                        }
                ) {

                    Row (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.label,
                            tint = if((menuItems.size-1) == index) RedColor1 else DisabledColor
                        )

                        Spacer(Modifier.width(24.dp))

                        Text(
                            text = item.label,
                            style = TextStyle(
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                color = if((menuItems.size-1) == index) RedColor1 else DisabledColor
                            )
                        )
                    }

                }
            }

            if ((menuItems.size-2) == index){
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(DisabledColor)
                )
                Spacer(Modifier.height(8.dp))
            } else {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerContentPreview(){
    GriyaBugarTheme {
        CmsScreen()
    }
}
