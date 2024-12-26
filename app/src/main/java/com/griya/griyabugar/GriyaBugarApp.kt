package com.griya.griyabugar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.screen.cms.CmsScreen
import com.griya.griyabugar.ui.screen.cms.paket.editpaket.EditPaketScreen
import com.griya.griyabugar.ui.screen.cms.paket.previewpaket.PreviewPaketScreen
import com.griya.griyabugar.ui.screen.cms.paket.tambahpaket.TambahPaketScreen
import com.griya.griyabugar.ui.screen.forgetPass.ForgetPasswordPart2
import com.griya.griyabugar.ui.screen.forgetPass.LupaPasswordScreen1
import com.griya.griyabugar.ui.screen.login.LoginScreen
import com.griya.griyabugar.ui.screen.main.MainScreen
import com.griya.griyabugar.ui.screen.main.myaccount.changepassword.ChangePasswordScreen
import com.griya.griyabugar.ui.screen.main.home.NotifikasiScreen
import com.griya.griyabugar.ui.screen.main.home.detailpaket.DetailPaketScreen
import com.griya.griyabugar.ui.screen.main.home.detailterapis.DetailTerapisScreen
import com.griya.griyabugar.ui.screen.main.myaccount.editprofile.EditProfileScreen
import com.griya.griyabugar.ui.screen.main.myaccount.informasigriya.InformasiGriyaScreen
import com.griya.griyabugar.ui.screen.register.RegisterScreen
import com.griya.griyabugar.ui.screen.splash.SplashScreen
import com.griya.griyabugar.ui.screen.welcome.WelcomeScreen
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun GriyaBugarApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){

//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route


    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ){
        composable(Screen.Login.route){
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route){
                        popUpTo(0){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToCms = {
                    navController.navigate(Screen.CMS.route){
                        popUpTo(Screen.CMS.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.Register.route){
            RegisterScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route){
                        popUpTo(0){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        composable(Screen.Splash.route){
            SplashScreen(
                onNavigateToWelcome = {
                    navController.navigate(Screen.Welcome.route){
                        popUpTo(Screen.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route){
                        popUpTo(Screen.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToCms = {
                    navController.navigate(Screen.CMS.route){
                        popUpTo(Screen.CMS.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.ForgotPassword.route){
            LupaPasswordScreen1(
                onNavigationBack = {
                    navController.popBackStack()
                },
                onNavigateToChangePassword = {
                    navController.navigate(Screen.ChangePassword.route)
                }
            )
        }
        composable(
            route = Screen.ChangePassword.route,
            arguments = listOf(
                navArgument("link"){
                    type = NavType.StringType
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://griyabugar.page.link?link={link}"
                }
            )
        ){ backStackEntry ->
            val link = backStackEntry.arguments?.getString("link")
            ForgetPasswordPart2(
                onNavigationBack = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route){
                        popUpTo(0){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                link = link
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
            MainScreen(
//                onNavigateToEditProfile = {
//                    navController.navigate(Screen.EditProfile.route)
//                }
                rootNavController = navController
            )
        }

        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                rootNavControll = navController
            )
        }

        composable(Screen.ChangePassword2.route){
            ChangePasswordScreen(
                rootNavControll = navController
            )
        }

        composable(Screen.InformasiGriya.route){
            InformasiGriyaScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Notifikasi.route) {
            NotifikasiScreen(
                rootNavControll = navController
            )
        }

        composable(Screen.DetailPaket.route) {
            DetailPaketScreen(
                rootNavControll = navController
            )
        }

        composable(Screen.DetailTerapis.route) {
            DetailTerapisScreen(
                rootNavControll = navController
            )
        }

        composable(Screen.CMS.route){
            CmsScreen(
                rootNavController = navController
            )
        }

        composable(Screen.TambahPaket.route){
           TambahPaketScreen(
               rootNavController = navController
           )
        }

        composable(
            route = Screen.EditPaket.route,
            arguments = listOf(navArgument("paketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val paketId = backStackEntry.arguments?.getString("paketId")
            if (paketId != null) {
                EditPaketScreen(
                    paketId = paketId,
                    rootNavController = navController
                )
            } else {
                // Tangani error, misalnya navigasi kembali jika paketId null
                navController.popBackStack()
            }
        }


        composable(Screen.PreviewPaket.route){
            PreviewPaketScreen(
                rootNavControll = navController
            )
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    GriyaBugarTheme {
        GriyaBugarApp()
    }
}