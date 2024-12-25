package com.griya.griyabugar.ui.components.cmspaket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun DiskonField(
    onChange: (String) -> Unit,
    value: String
){
    OutlinedTextField(
        value = value,
        onValueChange = {
            // Validasi input: hanya angka dan maksimum 100
            val filteredValue = it.filter { char -> char.isDigit() } // Hanya angka
            val numericValue = filteredValue.toIntOrNull() ?: 0 // Konversi ke Int (default 0 jika null)

            if (numericValue <= 100) { // Validasi maksimum 100
                onChange(filteredValue) // Update state dengan nilai yang valid
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        placeholder = {
            Text(
                text = "0",
                modifier = Modifier.fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        textStyle = TextStyle(
            textAlign = TextAlign.Start
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            cursorColor = Color.Black
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun DiskonFieldPreview(){
    GriyaBugarTheme {
        DiskonField(
            onChange = {},
            value = ""
        )
    }
}