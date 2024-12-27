package com.griya.griyabugar.ui.components.Button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun ButtonEdit(
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    val gradient = Brush.linearGradient(
        colors = listOf(GreenColor1, GreenColor2)
    )
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                brush = gradient,
                shape = RoundedCornerShape(5.dp)
            )
            .size(32.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_pensil),
            contentDescription = "Gradient Icon",
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center),
            tint = GreenColor1
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ButtonPreview(modifier: Modifier = Modifier) {
    GriyaBugarTheme {
        ButtonEdit(onClick = {}, modifier = Modifier)
    }
}