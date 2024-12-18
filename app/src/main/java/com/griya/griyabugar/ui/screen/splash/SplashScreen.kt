package com.griya.griyabugar.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.statusbar.UpdateStatusBarColor
import com.griya.griyabugar.ui.theme.GreenColor5
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToWelcome: () -> Unit,
    onNavigateToMain: () -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel()
){

    UpdateStatusBarColor(
        darkIcons = false
    )

    LaunchedEffect(true) {
        delay(2000)
        if(splashViewModel.getCurrentUser() !== null){
            onNavigateToMain()
        } else {
            onNavigateToWelcome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreenColor5),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(242.dp)
                .width(242.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    GriyaBugarTheme {
        SplashScreen(
            onNavigateToWelcome = {},
            onNavigateToMain = {}
        )
    }
}