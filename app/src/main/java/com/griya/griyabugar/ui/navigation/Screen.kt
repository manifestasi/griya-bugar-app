package com.griya.griyabugar.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
}