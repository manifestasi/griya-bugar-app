package com.griya.griyabugar.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.griya.griyabugar.ui.components.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.CircleColor

@Composable
fun CircleElement(
    size:Dp=200.dp,
    alignment: Alignment=Alignment.TopEnd,
    offsetX:Int=100,
    offsetY:Int=-100,
    startAngle:Float=180f,
    endAngle:Float=-90f
){
    Box(modifier = Modifier.fillMaxSize()){
        Canvas(
            modifier = Modifier
                .size(size)
                .align(alignment)
                .offset(x = (offsetX).dp, y = (offsetY).dp)
        ) {
            drawArc(
                color = CircleColor,
                startAngle = startAngle,
                sweepAngle = endAngle,
                useCenter = true
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    GriyaBugarTheme {
        CircleElement()
    }
}