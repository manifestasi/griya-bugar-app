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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.ui.components.Button.BoxButton
import com.griya.griyabugar.ui.components.Button.ButtonBack
import com.griya.griyabugar.ui.components.CircleElemen.CircleElement
import com.griya.griyabugar.ui.components.Field.EmailTextField
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.MainColor
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun LupaPasswordScreen1(
    onNavigationBack: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    forgetPassViewModel: ForgetPassViewModel = hiltViewModel()
){
    var email by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var isSuccess by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        forgetPassViewModel.sendForgotPasswordLinkEvent.collect { event ->
            when (event){
                is Resource.Loading -> {
                    isLoading = true
                }
                is Resource.Success -> {
                    isLoading = false
                    isSuccess = true
                }
                is Resource.Error -> {
                    isLoading = false
                    isError = true
                    errorMessage = event.errorMessage
                }
                else -> {
                    isLoading = false
                }
            }
        }
    }

    if(isSuccess){
        SuccessDialog(
            title = "Email Terkirim",
            description = "Cek email anda untuk ubah kata sandi",
            buttonText = "Ok",
            buttonOnClick = {
                isSuccess = false
            },
            onDismiss = {}
        )
    }

    if(isError){
        ErrorDialog(
            onDismiss = {},
            buttonText = "Coba lagi",
            title = "Kirim email link gagal",
            description = errorMessage,
            buttonOnClick = {
                isError = false
            }
        )
    }



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
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 50.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
        ){


            /*
            * Button Back
            * */
            ButtonBack(
                onClick = {
                    onNavigationBack()
                },
                padding = 0.dp
            )

            Spacer(modifier=Modifier.height(20.dp))

            Text("Lupa Kata Sandi",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier=Modifier.height(10.dp))
            Text("Lupa Kata Sandi",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier=Modifier
            ) {
                Text("Email",
                    fontFamily = poppins,
                    fontSize = 20.sp,

                    )
                Spacer(modifier=Modifier.height(5.dp))

                TextField(
                    placeHolder = "Masukkan email",
                    onChange = {
                        email = it
                    },
                    value = email
                )
            }

            Spacer(modifier=Modifier.height(30.dp))
            /*
            * Button untuk simpan
            * */
//                BoxButton(
//                    onClick = {},
//                    text = "Simpan",
//                    color = MainColor,
//                    fontColor = Color.White,
//                    width = 0.9f
//                )

            ButtonConfirm(
                onClick = {
                    forgetPassViewModel.sendForgotPasswordLink(email)
                },
                name = "Kirim",
                isDisabled = isLoading,
                isLoading = isLoading,
            )

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LupaPasswordPreview(){
    GriyaBugarTheme {
        LupaPasswordScreen1(
            onNavigationBack = {},
            onNavigateToChangePassword = {}
        )
    }
}