package com.griya.griyabugar.ui.screen.forgetPass

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.Button.BoxButton
import com.griya.griyabugar.ui.components.Button.ButtonBack
import com.griya.griyabugar.ui.components.CircleElemen.CircleElement
import com.griya.griyabugar.ui.components.Field.PasswordTextField
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.MainColor
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun ForgetPasswordPart2(
    modifier: Modifier = Modifier,

){

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val oldPass =  remember { mutableStateOf("") }
        val newPass =  remember { mutableStateOf("") }

        val visibility_state_old = remember { mutableStateOf(true) }
        val visibility_state_new = remember { mutableStateOf(true) }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            CircleElement()
            CircleElement(
                alignment = Alignment.BottomStart,
                startAngle = 0f,
                endAngle = 360f,
                offsetX = -130,
                offsetY = 80
            )

            Column (
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 50.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){


                /*
                * Button Back
                * */
                ButtonBack()

                Spacer(modifier=Modifier.height(20.dp))

                Text("Lupa Kata Sandi",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp)
                )
                Spacer(modifier=Modifier.height(10.dp))
                Text("Lupa Kata Sandi",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier=Modifier
                ) {
                    Text("Kata Sandi*",
                        fontFamily = poppins,
                        fontSize = 20.sp,

                        )
                    Spacer(modifier=Modifier.height(5.dp))

                    PasswordTextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        state = oldPass,
                        passwordVisible = visibility_state_old
                    )
                }

                Spacer(modifier=Modifier.height(10.dp))

                Column(
                    modifier=Modifier
                ) {
                    Text("Konfirmasi Kata Sandi*",
                        fontFamily = poppins,
                        fontSize = 20.sp,

                        )
                    Spacer(modifier=Modifier.height(5.dp))

                    PasswordTextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        state = newPass,
                        passwordVisible = visibility_state_new
                    )
                }
                Spacer(modifier=Modifier.height(30.dp))
                /*
                * Button untuk simpan
                * */
                BoxButton(
                    onClick = {},
                    text = "Simpan",
                    color = MainColor,
                    fontColor = Color.White,
                    width = 0.9f
                )

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForgetPreview(){
    GriyaBugarTheme {
        ForgetPasswordPart2()
    }
}
