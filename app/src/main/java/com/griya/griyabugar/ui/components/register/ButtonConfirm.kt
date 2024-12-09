package com.griya.griyabugar.ui.components.register

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.Brown
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun ButtonConfirm(
    onClick: () -> Unit,
    name: String
){
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Brown,
        )
    ) {
        Text(
            text = name,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = poppins,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonConfirmPreview(){
    GriyaBugarTheme {
        ButtonConfirm(
            name = "Masuk",
            onClick = {}
        )
    }
}