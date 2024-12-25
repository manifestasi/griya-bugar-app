package com.griya.griyabugar.ui.screen.cms.layanan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LayananScreen(
    innerPadding: PaddingValues
){
    Box(modifier = Modifier.padding(innerPadding)) {
        Text("Welcome to the Layanan screen!")
    }
}