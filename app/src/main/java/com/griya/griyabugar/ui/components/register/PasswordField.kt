package com.griya.griyabugar.ui.components.register

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun PasswordField(
    placeHolder: String,
    value: String,
    onChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = {
            onChange(it)
        },
        placeholder = { Text(placeHolder) },
        modifier = Modifier
            .fillMaxWidth(),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
            val description = if (isPasswordVisible) "Hide password" else "Show password"

            IconButton(onClick = { onVisibilityChange(!isPasswordVisible)}) {
                Icon(imageVector = icon, contentDescription = description)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordFieldPreview(){
    GriyaBugarTheme {
        PasswordField(
            placeHolder = "Masukkan Kata Sandi",
            value = "",
            onChange = {},
            isPasswordVisible = false,
            onVisibilityChange = {}
        )
    }
}