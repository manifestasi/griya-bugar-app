package com.griya.griyabugar.ui.screen.main.myaccount.changepassword

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.navigation.ChangePassScreen
import com.griya.griyabugar.ui.screen.main.myaccount.changepassword.enteremailpass.EnterEmailPassScreen
import com.griya.griyabugar.ui.screen.main.myaccount.changepassword.enteroldpass.EnterOldPassScreen
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun ChangePasswordScreen(
    rootNavControll: NavHostController = rememberNavController(),
    changePassNavController: NavHostController = rememberNavController()
){
    val navBackStackEntry by changePassNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarWithBackButton(
                title = "Ubah Kata Sandi",
                onClickBack = {
                    if(currentRoute == changePassNavController.graph.startDestinationRoute){
                        rootNavControll.popBackStack()
                    } else {
                        changePassNavController.popBackStack()
                    }
                }
            )
        }
    ) { innerPadding ->

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = changePassNavController,
            startDestination = ChangePassScreen.oldPasswordEnter.route
        ){
            composable(ChangePassScreen.oldPasswordEnter.route){
                EnterOldPassScreen(
                    onNavigateToEnterEmail = {
                        changePassNavController.navigate(ChangePassScreen.EmailEnter.route)
                    }
                )
            }

            composable(ChangePassScreen.EmailEnter.route){
                EnterEmailPassScreen()
            }
        }
    }
}

@Preview
@Composable
fun ChangePasswordScreenPreview(){
    GriyaBugarTheme {
        ChangePasswordScreen()
    }
}