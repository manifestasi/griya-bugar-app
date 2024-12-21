package com.griya.griyabugar.ui.components.CircleElemen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2

@Composable
fun CircleElement2(
    modifier: Modifier,
    startAngle:Float=180f,
    endAngle:Float=-90f,
    colors: List<Color> = listOf(
        GreenColor1,
        GreenColor2
    )
){
    Canvas(
        modifier = modifier
    ) {
        drawArc(
            brush = Brush.linearGradient(
                colors = colors
            ),
            startAngle = startAngle,
            sweepAngle = endAngle,
            useCenter = true
        )
    }
}