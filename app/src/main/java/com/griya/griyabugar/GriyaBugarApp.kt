package com.griya.griyabugar

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
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.forgetPass.ForgetPasswordPart2
import com.griya.griyabugar.ui.screen.forgetPass.LupaPasswordScreen1
import com.griya.griyabugar.ui.screen.login.LoginScreen
import com.griya.griyabugar.ui.screen.main.MainScreen
import com.griya.griyabugar.ui.screen.register.RegisterScreen
import com.griya.griyabugar.ui.screen.splash.SplashScreen
import com.griya.griyabugar.ui.screen.welcome.WelcomeScreen
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun GriyaBugarApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route

        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = modifier.padding(innerPadding)
        ){
            composable(Screen.Login.route){
                LoginScreen(
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    },
                    onNavigateToForgotPassword = {
                        navController.navigate(Screen.ForgotPassword.route)
                    }
                )
            }
            composable(Screen.Register.route){
                RegisterScreen()
            }
            composable(Screen.Splash.route){
                SplashScreen(
                    onNavigateToWelcome = {
                        navController.navigate(Screen.Welcome.route){
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.ForgotPassword.route){
                LupaPasswordScreen1()
            }
            composable(Screen.ChangePassword.route){
                ForgetPasswordPart2(
                    onNavigationBack = {
                        navController.popBackStack()
                    },
                    onNavigationChangePassword = {
                        navController.navigate(Screen.ChangePassword.route)
                    }
                )
            }
            composable(Screen.Welcome.route){
                WelcomeScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
            composable(Screen.Main.route){
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GriyaBugarTheme {
        GriyaBugarApp()
    }
}