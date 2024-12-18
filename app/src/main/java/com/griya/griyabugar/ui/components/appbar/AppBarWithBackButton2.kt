package com.griya.griyabugar.ui.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.Brown
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun AppBarWithBackButton2(
    title: String,
    onClickBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = Brush.linearGradient(listOf(GreenColor1, GreenColor2)))
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(bottomEnd = 5.dp, bottomStart = 5.dp)
            )
    ) {

        Spacer(Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { onClickBack() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "arrow back",
                    tint = TextColorWhite
                )
            }

            Spacer(Modifier.width(22.dp))

            Text(
                text = title,
                fontFamily = poppins,
                color = TextColorWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold

            )

        }


    }
}

@Preview(showBackground = true)
@Composable
fun AppBarWithBackButtonPreview2() {
    GriyaBugarTheme {
        AppBarWithBackButton2(
            title = "test title",
            onClickBack = {}
        )
    }
}