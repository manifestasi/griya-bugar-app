package com.griya.griyabugar.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.TextColorWhite

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .padding(top = 40.dp, start = 16.dp)
        .clip(RoundedCornerShape(5.dp))
        .background(TextColorWhite)
        .size(30.dp)
        .clickable {
            onClick()
        })
    {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = null,
            tint = TextColorBlack,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Center)
        )
    }
}