package com.griya.griyabugar.ui.components.Button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.HijauMuda
import com.griya.griyabugar.ui.theme.HijauTua

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

@Composable
fun ButtonGradientBorder(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    borderColor: List<Color> = listOf(HijauMuda, HijauTua),
    size:Dp = 30.dp,
    rounded: Dp = 5.dp,
    onClick: ()->Unit,
    content:@Composable ()-> Unit
){
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .background(color = color)
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    brush = Brush.linearGradient(borderColor)
                ),
                shape = RoundedCornerShape(corner = CornerSize(rounded))
            ).clickable {
                onClick()
            }
    ){
       content()
    }
}