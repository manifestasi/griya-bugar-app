package com.griya.griyabugar.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.griya.griyabugar.R
import com.griya.griyabugar.data.respository.AuthRepository
import com.griya.griyabugar.ui.components.statusbar.UpdateStatusBarColor
import com.griya.griyabugar.ui.theme.GreenColor5
import com.griya.griyabugar.ui.theme.GreenColor7
import com.griya.griyabugar.ui.theme.GreenColor8
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.util.Preferences
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToWelcome: () -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToCms: () -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel()
){

    val context = LocalContext.current

    UpdateStatusBarColor(
        darkIcons = false
    )

    LaunchedEffect(true) {
        delay(2000)
        if(splashViewModel.getCurrentUser() !== null){
            val role = Preferences.getFromPreferences(context, AuthRepository.ROLE)
            if (role != null){
                if (role == AuthRepository.CUSTOMER){
                    onNavigateToMain()
                } else if (role == AuthRepository.ADMIN){
                    onNavigateToCms()
                }
            }
        } else {
            onNavigateToWelcome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(
                colors = listOf(
                    GreenColor7,
                    GreenColor8
                )
            )),
        contentAlignment = Alignment.Center
    ){
//        CircleElement2(
//            modifier = Modifier
//                .size(200.dp)
//                .align(Alignment.TopEnd)
//                .offset(x = (100).dp, y = (-100).dp),
//            colors = listOf(
//                GreenColor3,
//                GreenColor3
//            )
//        )
//
//        CircleElement2(
//            modifier = Modifier
//                .size(258.dp),
//            startAngle = 0f,
//            endAngle = 360f,
//            colors = listOf(
//                GreenColor3,
//                GreenColor3
//            )
//        )
//
//        CircleElement2(
//            modifier = Modifier
//                .size(118.dp)
//                .align(Alignment.BottomEnd)
//                .offset(x = (-34).dp, y = (-120).dp),
//            startAngle = 0f,
//            endAngle = 360f,
//            colors = listOf(
//                GreenColor3,
//                GreenColor3
//            )
//        )

        Image(
            painter = painterResource(R.drawable.logo_new),
            contentDescription = "Logo",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    GriyaBugarTheme {
        SplashScreen(
            onNavigateToWelcome = {},
            onNavigateToMain = {},
            onNavigateToCms = {}
        )
    }
}