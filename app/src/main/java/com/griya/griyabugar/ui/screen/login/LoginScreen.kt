package com.griya.griyabugar.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.EmailTextField
import com.griya.griyabugar.ui.components.PasswordTextField

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(Modifier.height(40.dp))
        HeaderSection(modifier = Modifier .padding(16.dp))
        MainSection(modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun HeaderSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Masuk",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_bold))),
            fontSize = 24.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Masuk untuk melanjutkan",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
            fontSize = 24.sp,
            color = Color.Black
        )

    }
}

@Composable
private fun MainSection (modifier: Modifier = Modifier) {
    val emailState = remember { mutableStateOf("") }
    val state = remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Text(
            text = "Email",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(10.dp))

        EmailTextField(
            state = emailState
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Kata Sandi",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(10.dp))

        PasswordTextField(
            state = state,
            passwordVisible = passwordVisible,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Lupa Kata Sandi",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_medium))),
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.End).clickable {  }
        )
        Spacer(Modifier.height(30.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Cyan,
            )
        ) {
            Text(
                text = "Masuk",
                fontFamily = FontFamily(listOf(Font(R.font.poppins_medium))),
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(Modifier.height(10.dp))
        Row(modifier = Modifier.align(CenterHorizontally)) {
            Text(
                text = "Belum memiliki akun?",
                fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = "Daftar",
                fontFamily = FontFamily(listOf(Font(R.font.poppins_semibold))),
                fontSize = 16.sp,
                color = Color.Black,
                modifier=Modifier.clickable {  }
            )

        }
    }
}
@Preview(showBackground = true)
@Composable
fun HeaderPreview(modifier: Modifier = Modifier) {
    HeaderSection()
}