package com.griya.griyabugar.ui.screen.main.myaccount.changepassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.PasswordField
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun ChangePasswordScreen(
    rootNavControll: NavHostController = rememberNavController()
){
    Scaffold(
        topBar = {
            AppBarWithBackButton(
                title = "Ubah Kata Sandi",
                onClickBack = {
                    rootNavControll.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(32.dp))

            Column {
                Text(
                    text = "Kata Sandi Lama",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight(400),
                        fontSize = 20.sp
                    )
                )
                Spacer(Modifier.height(8.dp))
                PasswordField(
                    placeHolder = "Masukkan Kata Sandi Lama",
                    value = "",
                    onChange = {},
                    isPasswordVisible = false,
                    onVisibilityChange = {

                    }
                )
            }

            Spacer(Modifier.height(36.dp))

            ButtonConfirm(
                onClick = {},
                name = "Simpan",
                isDisabled = false
            )
        }
    }
}

@Preview
@Composable
fun ChangePasswordScreenPreview(){
    GriyaBugarTheme {
        ChangePasswordScreen()
    }
}