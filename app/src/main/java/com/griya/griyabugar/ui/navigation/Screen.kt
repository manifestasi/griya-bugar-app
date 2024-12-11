package com.griya.griyabugar.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("main/home")
    object Order: Screen("main/order")
    object MyAccount: Screen("main/myaccount")
    object Login : Screen("login")
    object Register: Screen("register")
    object Splash: Screen("splash")
    object ForgotPassword: Screen("forgotPassword")
    object ChangePassword: Screen("changePassword")
    object Welcome: Screen("welcome")
    object Main: Screen("main")
}