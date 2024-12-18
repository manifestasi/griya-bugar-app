package com.griya.griyabugar.ui.components.promo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.poppins

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServiceRow(
    items: List<String>,
    maxItem: Int,
    fontSize: Int = 12,
    modifier: Modifier = Modifier) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = maxItem,
    ) {
        items.forEach { text ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp) // Ukuran titik
                        .clip(CircleShape)
                        .background(DisabledColor) // Warna titik hijau gelap
                )
                Spacer(modifier = Modifier.width(4.dp)) // Jarak antara titik dan teks
                Text(
                    text = text,
                    color = TextColorBlack,
                    fontFamily = poppins,
                    fontSize = fontSize.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}