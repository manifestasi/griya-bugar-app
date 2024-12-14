package com.griya.griyabugar.ui.components.Button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R

@Composable
fun ButtonBorder(
    modifier: Modifier = Modifier,
    text:String,
    color: Color = Color.White,
    fontColor: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    borderColor: Color = Color.Black,
    height: Dp = 55.dp,
    rounded: Dp =10.dp,
    onClick: ()->Unit,
){
    Button (
        onClick = onClick,
        shape = RoundedCornerShape(rounded),
        colors = ButtonDefaults.buttonColors(color),
        border = BorderStroke(2.dp, borderColor),
        modifier = modifier
            .fillMaxWidth()
            .height(height)

    ){
        Text(
            text,
            color = fontColor,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
        )
    }

}