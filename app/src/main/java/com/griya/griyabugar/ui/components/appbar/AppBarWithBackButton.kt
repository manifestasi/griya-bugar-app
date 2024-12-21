package com.griya.griyabugar.ui.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.Brown
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.HijauMuda
import com.griya.griyabugar.ui.theme.HijauTua
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun AppBarWithBackButton(
    title: String,
    onClickBack: () -> Unit
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
            .clip(RoundedCornerShape(
                bottomEnd = 3.dp,
                bottomStart = 3.dp
            )),
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
                            HijauTua,
                            HijauMuda
                        )
                    )
                )

//            .drawBehind {
//                val strokeWidth = 2.dp.toPx() // Ketebalan garis bawah
//                val y = size.height - strokeWidth / 2 // Posisi garis di bagian bawah
//                drawLine(
//                    color = Brown, // Warna garis bawah
//                    start = Offset(0f, y), // Titik awal garis (kiri bawah)
//                    end = Offset(size.width, y), // Titik akhir garis (kanan bawah)
//                    strokeWidth = strokeWidth
//                )
//            }
        ) {

            Spacer(Modifier.height(26.dp))

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {onClickBack()}) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "arrow back",
                        tint = Color.White
                    )
                }

                Spacer(Modifier.width(22.dp))

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
fun AppBarWithBackButtonPreview(){
    GriyaBugarTheme {
        AppBarWithBackButton(
            title = "test title",
            onClickBack = {}
        )
    }
}