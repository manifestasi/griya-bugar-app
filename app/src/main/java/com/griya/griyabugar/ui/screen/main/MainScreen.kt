package com.griya.griyabugar.ui.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.data.model.BottomNavItem
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.main.home.HomeScreen
import com.griya.griyabugar.ui.screen.main.myaccount.MyAccountScreen
import com.griya.griyabugar.ui.screen.main.order.OrderScreen
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun MainScreen(
    rootNavController: NavHostController = rememberNavController(),
    navController: NavHostController = rememberNavController(),
//    onNavigateToEditProfile: () -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController = navController)
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
                OrderScreen()
            }

            composable(Screen.MyAccount.route){
                MyAccountScreen(
//                    onNavigateToEditProfile = onNavigateToEditProfile
                    rootNavController = rootNavController
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController){
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val bottomNavItems = listOf(
            BottomNavItem(Screen.Home.route, Icons.Default.Home, "Beranda"),
            BottomNavItem(Screen.Order.route, Icons.Default.Person, "Pemesanan"),
            BottomNavItem(Screen.MyAccount.route, Icons.Default.Settings, "Akun Saya")
        )

        bottomNavItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                    )
                },
                label = {
                    Text(item.label)
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
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