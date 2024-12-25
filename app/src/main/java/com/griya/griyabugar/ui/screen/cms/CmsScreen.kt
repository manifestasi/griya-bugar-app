package com.griya.griyabugar.ui.screen.cms

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.data.model.NavDrawerItem
import com.griya.griyabugar.ui.components.appbar.AppBarWithDrawer
import com.griya.griyabugar.ui.components.statusbar.UpdateStatusBarColor
import com.griya.griyabugar.ui.navigation.NavDrawScreen
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.cms.layanan.LayananScreen
import com.griya.griyabugar.ui.screen.cms.paket.PaketScreen
import com.griya.griyabugar.ui.screen.cms.pelanggan.PelangganScreen
import com.griya.griyabugar.ui.screen.cms.terapis.TerapisScreen
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.RedColor1
import com.griya.griyabugar.ui.theme.poppins
import kotlinx.coroutines.launch

@Composable
fun CmsScreen(
    rootNavController: NavHostController = rememberNavController()
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by rememberSaveable { mutableStateOf(NavDrawScreen.Pelanggan.route) }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    UpdateStatusBarColor(
        darkIcons = false
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectedItem = selectedItem,
                onItemClick = { item ->
                    selectedItem = item
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {

        Scaffold(
            topBar = {
                AppBarWithDrawer(
                    title = "Test",
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
                }
            },
        ) { innerPadding ->

            if (selectedItem == NavDrawScreen.Pelanggan.route){
                PelangganScreen(innerPadding)
            } else if (selectedItem == NavDrawScreen.Paket.route){
                PaketScreen(innerPadding)
            } else if (selectedItem == NavDrawScreen.Layanan.route){
                LayananScreen(innerPadding)
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
    onItemClick: (String) -> Unit
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
