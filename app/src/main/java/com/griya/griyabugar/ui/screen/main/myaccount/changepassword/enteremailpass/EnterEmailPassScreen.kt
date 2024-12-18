package com.griya.griyabugar.ui.screen.main.myaccount.changepassword.enteremailpass

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun EnterEmailPassScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {

        Spacer(Modifier.height(32.dp))

        Column {

            Text(
                text = "Email",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight(400),
                    fontSize = 20.sp
                )
            )

            Spacer(Modifier.height(8.dp))

            TextField(
                onChange = {},
                value = "",
                placeHolder = "Masukkan Email"
            )

        }

        Spacer(Modifier.height(36.dp))

        ButtonConfirm(
            onClick = {},
            name = "Selanjutnya",
            isDisabled = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EnterEmailPassPreview(){
    GriyaBugarTheme {
        EnterEmailPassScreen()
    }
}