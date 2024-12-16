package com.griya.griyabugar.ui.components.promo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.Yellow
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun Rating(
    rate:String,
    modifier: Modifier = Modifier) {
    Row(){
        Icon(
            painterResource(R.drawable.ic_rate),
            contentDescription = "Location",
            tint = Yellow,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = rate,
            color = TextColorBlack,
            fontSize = 12.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
        )
    }
}