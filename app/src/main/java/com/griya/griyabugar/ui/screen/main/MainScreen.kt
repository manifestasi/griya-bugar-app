package com.griya.griyabugar.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.data.model.ItemPemesananModel
import com.griya.griyabugar.ui.components.appbar.AppBarBasic
import com.griya.griyabugar.ui.components.appbar.AppBarHome
import com.griya.griyabugar.ui.components.bottomnavbar.BottomNavigationBar
import com.griya.griyabugar.ui.components.loading.LoadingAnimation
import com.griya.griyabugar.ui.components.statusbar.UpdateStatusBarColor
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.main.home.HomeScreen
import com.griya.griyabugar.ui.screen.main.myaccount.MyAccountScreen
import com.griya.griyabugar.ui.screen.main.order.PemesananScreen
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun MainScreen(
    rootNavController: NavHostController = rememberNavController(),
    navController: NavHostController = rememberNavController(),
//    onNavigateToEditProfile: () -> Unit
){
    UpdateStatusBarColor(
        darkIcons = false
    )

    var isLoading by rememberSaveable { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute
            )
        },
        topBar = {
            if (currentRoute == Screen.MyAccount.route){
                AppBarBasic(
                    title = "Akun Saya"
                )
            } else if (currentRoute == Screen.Home.route){
                AppBarHome(
                    rootNavController = rootNavController,
                    Modifier.background(
                        brush = Brush.linearGradient(
                            listOf(
                                GreenColor1,
                                GreenColor2
                            )
                        )
                    )
                )
            } else if (currentRoute == Screen.Order.route){
                AppBarBasic(
                    title = "Pemesanan"
                )
            }
        }

    ) { innerPadding ->
        NavHost (
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route){
                HomeScreen(
                    rootNavController = rootNavController
                )
            }

            composable(Screen.Order.route){

                PemesananScreen(
                    modifier = Modifier,
                    rootNavControll = rootNavController

                )
            }

            composable(Screen.MyAccount.route){
                MyAccountScreen(
//                    onNavigateToEditProfile = onNavigateToEditProfile
                    rootNavController = rootNavController,
                    isLoading = isLoading,
                    onLoadingChange = {
                        isLoading = it
                    }
                )
            }
        }
    }

    /*  Digunakan untuk loading */
    if(isLoading){
        LoadingAnimation()
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    GriyaBugarTheme {
        MainScreen(
//            onNavigateToEditProfile = {}
        )
    }
}