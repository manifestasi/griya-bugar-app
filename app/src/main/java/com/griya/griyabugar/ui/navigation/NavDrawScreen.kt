package com.griya.griyabugar.ui.navigation

sealed class NavDrawScreen(val route: String) {
    object Terapis : NavDrawScreen("terapis")
    object Paket : NavDrawScreen("paket")
    object Layanan : NavDrawScreen("layanan")
    object Pelanggan : NavDrawScreen("pelanggan")
}