package com.griya.griyabugar.ui.components.checkbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.griya.griyabugar.R

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
){
    Box(
        modifier = Modifier.clickable {
            onCheckedChange(!checked)
        }
    ) {
        if (!checked){
            Image(
                painter = painterResource(R.drawable.ic_notselected),
                contentDescription = "not selected"
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_selected),
                contentDescription = "selected"
            )
        }
    }
}