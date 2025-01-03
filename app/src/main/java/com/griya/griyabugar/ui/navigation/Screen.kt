package com.griya.griyabugar.ui.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")
    object Register: Screen("register")
    object Splash: Screen("splash")
    object ForgotPassword: Screen("forgotPassword")
    object ChangePassword: Screen("changePassword?link={link}")
    object Welcome: Screen("welcome")

    /* Main screen */
    object Main: Screen("main")
    object Home : Screen("main/home")
    object Order: Screen("main/order")
    object MyAccount: Screen("main/myaccount")

    /* CMS Screen / Admin Screen / Dashboard */
    object CMS : Screen("cms")
    object EditTerapis : Screen("editTerapis")
    object PreviewTerapis : Screen("previewTerapis")

    /* Profile screen */
    object EditProfile: Screen("editProfile")

    /* change pass when login */
    object ChangePassword2 : Screen("changePassword2")

    /* informasi griya screen */
    object InformasiGriya : Screen("informasiGriya")

    /* Notifikasi screen */
    object Notifikasi: Screen("notifikasi")

    /*Detail Paket screen*/
    object DetailPaket: Screen("detailPaket")

   /*Detail Terapis screen*/
    object DetailTerapis: Screen("detailTerapis")
}