package com.griya.griyabugar.ui.components.register

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun TextField(
    onChange: (String) -> Unit,
    value: String = "",
    placeHolder: String,
    readOnly: Boolean = false
){
    OutlinedTextField(
        value = value,
        readOnly = readOnly,
        onValueChange = {
            onChange(it)
        },
        placeholder = { Text(
            placeHolder,
            style = TextStyle(
                fontFamily = poppins,
                color = DisabledColor,
                fontSize = 16.sp
            )
        ) },
        modifier = Modifier
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}