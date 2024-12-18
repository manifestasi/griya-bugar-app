package com.griya.griyabugar.ui.components.Button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun ButtonGradient(
    onClick: () -> Unit,
    name: String,
    height: Dp = 55.dp,
    rounded:Dp=10.dp,
    width:Float = 0.9f,
){
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .drawBehind {
                val shadowColor = Color(0x28000000)
                val shadowRadius = 10.dp.toPx()
                val offsetX = 0.dp.toPx()
                val offsetY = (2.5).dp.toPx()
                drawRoundRect(
                    color = shadowColor,
                    topLeft = Offset(x = offsetX, y = offsetY),
                    size = Size(size.width + 2.dp.toPx(), size.height),
                    cornerRadius = CornerRadius(x = shadowRadius, y = shadowRadius),
                    style = Fill,
                    alpha = 1f,
                    blendMode = BlendMode.SrcOver,
                )
            },
        shape = RoundedCornerShape(rounded),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            GreenColor1,
                            GreenColor2
                        )
                    )
                )
        ) {
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = poppins,
                ),
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}