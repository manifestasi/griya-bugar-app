package com.griya.griyabugar.ui.components.promo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun DiskonBox(
    text:String,
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp, brush = Brush.linearGradient(
                    colors = listOf(
                        GreenColor1, GreenColor2
                    )
                ), shape = RoundedCornerShape(5.dp)
            )
            .padding(5.dp)
    ) {
        Text(
            text = text,
            color = GreenColor1,
            fontSize = 12.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        GreenColor1, GreenColor2
                    )
                )
            )
        )
    }
}