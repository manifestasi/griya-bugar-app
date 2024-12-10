package com.griya.griyabugar.ui.components.Button

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.griya.griyabugar.ui.components.Button.ui.theme.GriyaBugarTheme


@Composable
fun ButtonBack(
    alignment: Alignment=Alignment.TopStart,
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    padding:Dp = 10.dp

) {
    Box(
        modifier = modifier.fillMaxWidth().padding(start = padding),
        contentAlignment = alignment
    ){
        IconButton(
            onClick = {},
            modifier = Modifier.size(size)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                tint = Color.Black,
                contentDescription = "back_button",
                modifier = Modifier.size(size)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview3() {
    GriyaBugarTheme {
        ButtonBack()
    }
}