package com.griya.griyabugar.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.Button.BoxButton
import com.griya.griyabugar.ui.components.CircleElemen.CircleElement
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.MainColor

//class WelcomeActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            GriyaBugarTheme {
//                MainWelcomeScreen(modifier=Modifier)
//            }
//        }
//    }
//}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        CircleElement()
        CircleElement(
            alignment = Alignment.BottomStart,
            startAngle = 0f,
            endAngle = 360f,
            offsetX = -80,
            offsetY = 80
            )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "Selamat Datang",
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp
            )
            Text(
                "Masuk atau Daftar untuk melanjutkan aplikasi",
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(25.dp))
            BoxButton(
                text = "Masuk",
                color = MainColor,
                fontColor = Color.White,
                width = 0.98f,
                onClick = {},
            )
            Spacer(modifier = Modifier.height(10.dp))
            BoxButton(
                text = "Daftar",
                color = Color.White,
                fontColor = Color.Black,
                width = 0.98f,
                isBorder = true,
                onClick = {},
            )
        }
    }
}


@Composable
fun MainWelcomeScreen(modifier: Modifier){
    Scaffold { innerPadding->
        WelcomeScreen(
            modifier = modifier.padding(innerPadding)
        )
    }
    
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomePreview() {
    GriyaBugarTheme {
        MainWelcomeScreen(modifier = Modifier)
    }
}