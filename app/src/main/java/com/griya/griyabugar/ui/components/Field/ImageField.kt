package com.griya.griyabugar.ui.components.Field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun ImageField(
    value: String,
    placeholder: String,
    onClick: () -> Unit
){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        value = value,
        onValueChange = {},
        readOnly = true,
        enabled = false,
        trailingIcon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_jpg),
                contentDescription = "clock",
                tint = DisabledColor
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            cursorColor = Color.Black
        ),
        placeholder = { Text(
            text = placeholder,
            style = TextStyle(
                fontFamily = poppins,
                color = DisabledColor,
                fontSize = 16.sp
            )
        ) }
    )
}