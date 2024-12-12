package com.griya.griyabugar.ui.screen.changePasswd

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.griya.griyabugar.ui.components.Field.PasswordTextField
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.MainColor
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun ChangePasswdLayout(
    modifier:Modifier = Modifier
){
    var pass_state = remember { mutableStateOf("") }
    var visibility_state_old = remember { mutableStateOf(true) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 30.dp).fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        ){
            Text("Kata Sandi*",
                fontFamily = poppins,
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
        }

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            state = pass_state,
            passwordVisible = visibility_state_old
        )
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.fillMaxWidth()
                .padding(top=10.dp, end = 20.dp)

        ){
            Text("Lupa Kata Sandi?",
                fontFamily = poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.clickable {
                    /*
                    * Navigasi ke lupa password
                    * */
                }
            )
        }

        BoxButton(
            text = "Selanjutnya",
            color = MainColor,
            fontColor = Color.White,
            width = 0.9f,
            modifier = Modifier.padding(top = 30.dp),
            onClick = {
                /*
                * Navigasi ke isi Email
                * */
            }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswdScreen(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /*
                        *  Button kembali
                        * */
                        ButtonBack(
                            modifier = Modifier.fillMaxWidth(0.2f),
                            onClick = {}
                        )

                        Text("Ubah Kata Sandi", 
                            fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            color = Color.Black
                        )


                    }
                }
            )
        },
        content = {
            innerPadding->
            ChangePasswdLayout(
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChangePasswdPreview(){
    GriyaBugarTheme {
        ChangePasswdScreen()
    }
}