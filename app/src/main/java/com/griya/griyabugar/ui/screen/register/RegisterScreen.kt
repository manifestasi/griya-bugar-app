package com.griya.griyabugar.ui.screen.register

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.CircleElemen.CircleElement
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.NoHandPhoneField
import com.griya.griyabugar.ui.components.register.PasswordField
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.theme.Gray
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun RegisterScreen(){
    var nama by rememberSaveable { mutableStateOf("") }
    var noTelepon by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var visiblePassword by rememberSaveable { mutableStateOf(false) }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var visibleConfirmPassword by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
//            Canvas(
//                modifier = Modifier
//                    .size(150.dp)
//                    .align(Alignment.TopEnd)
//                    .offset(x = 50.dp, y = (-65).dp)
//            ) {
//                drawCircle(
//                    color = Gray, // Warna lingkaran
//                    radius = size.minDimension / 2 // Setengah dari ukuran canvas
//                )
//            }
//
//            Canvas(
//                modifier = Modifier
//                    .size(150.dp)
//                    .align(Alignment.BottomStart)
//                    .offset(x = (-90).dp, y = 80.dp)
//            ){
//                drawCircle(
//                    color = Gray,
//                    radius = size.minDimension / 2
//                )
//            }

            CircleElement()
            CircleElement(
                alignment = Alignment.BottomStart,
                startAngle = 0f,
                endAngle = 360f,
                offsetX = -130,
                offsetY = 80
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center)
            ) {
                Column {
                    Text(
                        text = "Daftar",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 24.sp,
                            lineHeight = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                top = 8.dp
                            ),
                        text = "Daftar untuk melanjutkan",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(400)
                        )
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
                Column {
                    Text(
                        text = "Nama*",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        onChange = {
                            nama = it
                        },
                        value = nama,
                        placeHolder = "Masukkan Nama"
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
                Column {
                    Text(
                        text = "Nomor Telepon*",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    NoHandPhoneField(
                        onChange = {
                            noTelepon = it
                        },
                        value = noTelepon
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
                Column {
                    Text(
                        text = "Email*",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        onChange = {
                            email = it
                        },
                        placeHolder = "Masukkan Email",
                        value = email
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
                Column {
                    Text(
                        text = "Kata Sandi*",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    PasswordField(
                        placeHolder = "Masukkan Kata Sandi",
                        value = password,
                        onChange = {
                            password = it
                        },
                        isPasswordVisible = visiblePassword,
                        onVisibilityChange = {
                            visiblePassword = it
                        }
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
                Column {
                    Text(
                        text = "Konfirmasi Kata Sandi*",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    PasswordField(
                        placeHolder = "Konfirmasi Kata Sandi",
                        value = confirmPassword,
                        onChange = {
                            confirmPassword = it
                        },
                        isPasswordVisible = visibleConfirmPassword,
                        onVisibilityChange = {
                            visibleConfirmPassword = it
                        }
                    )
                }
                Spacer(Modifier.height(42.dp))
                ButtonConfirm(
                    onClick = {},
                    name = "Daftar"
                )
                Spacer(Modifier.height(24.dp))
                Row(modifier = Modifier.align(CenterHorizontally)) {
                    Text(
                        text = "Belum memiliki akun?",
                        fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "Masuk",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier=Modifier.clickable {  }
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    GriyaBugarTheme {
        RegisterScreen()
    }
}