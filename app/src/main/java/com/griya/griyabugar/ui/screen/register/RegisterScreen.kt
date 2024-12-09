package com.griya.griyabugar.ui.screen.register

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.Gray
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun RegisterScreen(){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Canvas(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 50.dp, y = (-65).dp)
            ) {
                drawCircle(
                    color = Gray, // Warna lingkaran
                    radius = size.minDimension / 2 // Setengah dari ukuran canvas
                )
            }

            Canvas(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = (-70).dp, y = 40.dp)
            ){
                drawCircle(
                    color = Gray,
                    radius = size.minDimension / 2
                )
            }

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
                            fontSize = 24.sp,
                            lineHeight = 36.sp,
                            fontWeight = FontWeight(700)
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                top = 8.dp
                            ),
                        text = "Daftar untuk melanjutkan",
                        style = TextStyle(
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
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {  },
                        placeholder = { Text("Masukkan Nama") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 8.dp
                            )
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
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {  },
                        placeholder = {
                            Text(
                                text = "81xxxxxxx",
                                modifier = Modifier.fillMaxHeight()
                                    .wrapContentHeight(Alignment.CenterVertically)
                                    .padding(start = 16.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(
                                top = 8.dp
                            ),
                        leadingIcon = {
                            Row {
                                Text(
                                    text = "+62",
                                    modifier = Modifier.fillMaxHeight()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                        .padding(start = 12.dp)
                                )
                                Spacer(
                                    modifier = Modifier
                                        .width(16.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                        .padding(
                                            top = 7.dp,
                                            bottom = 7.dp,
                                        )
                                        .background(color = Color.Gray)
                                )
                            }
                        }
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
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp
                        )
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {  },
                        placeholder = { Text("Masukkan Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 8.dp
                            )
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
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