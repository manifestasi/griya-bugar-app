package com.griya.griyabugar.ui.navigation

sealed class ChangePassScreen(val route: String) {
    /* Change password */
    object oldPasswordEnter : ChangePassScreen("passwordEnter")
    object EmailEnter : ChangePassScreen("emailEnter")
    object NewPasswordEnter : ChangePassScreen("newPasswordEnter")
}