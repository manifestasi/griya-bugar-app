package com.griya.griyabugar.ui.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun AppBarBasic(
    title: String,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .drawBehind {
                val shadowColor = Color(0x33000000) // Warna bayangan
                val shadowHeight = 5.dp.toPx() // Tinggi bayangan
                val gradientBrush = Brush.verticalGradient(
                    colors = listOf(
                        shadowColor, // Warna solid di awal
                        Color.Transparent // Transparan di akhir
                    ),
                    startY = size.height,
                    endY = size.height + shadowHeight
                )
                drawRect(
                    brush = gradientBrush,
                    topLeft = Offset(0f, size.height),
                    size = Size(size.width, shadowHeight)
                )
            }
            .clip(
                RoundedCornerShape(
                bottomEnd = 3.dp,
                bottomStart = 3.dp
            )
            ),
        shape = RoundedCornerShape(
            bottomEnd = 3.dp,
            bottomStart = 3.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
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

            Spacer(Modifier.height(26.dp))

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

            }

            Spacer(Modifier.height(26.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarBasicPreview(){
    GriyaBugarTheme {
        AppBarBasic(
            title = "Test"
        )
    }
}