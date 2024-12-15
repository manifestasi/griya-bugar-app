package com.griya.griyabugar.ui.components.appbar

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.Brown
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun AppBarWithBackButton(
    title: String,
    onClickBack: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .clip(RoundedCornerShape(
                bottomEnd = 3.dp,
                bottomStart = 3.dp
            ))
            .drawBehind {
                val strokeWidth = 2.dp.toPx() // Ketebalan garis bawah
                val y = size.height - strokeWidth / 2 // Posisi garis di bagian bawah
                drawLine(
                    color = Brown, // Warna garis bawah
                    start = Offset(0f, y), // Titik awal garis (kiri bawah)
                    end = Offset(size.width, y), // Titik akhir garis (kanan bawah)
                    strokeWidth = strokeWidth
                )
            }
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
                    tint = Color.Unspecified
                )
            }

            Spacer(Modifier.width(22.dp))

            Text(
                text = title,
                style = TextStyle(
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

        }

        Spacer(Modifier.height(26.dp))

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