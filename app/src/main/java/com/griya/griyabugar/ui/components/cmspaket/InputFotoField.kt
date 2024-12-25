package com.griya.griyabugar.ui.components.cmspaket

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun InputFotoField(
    onUploadClick: () -> Unit, // Callback saat ikon di klik
    value: String = "",
    placeHolder: String,
    readOnly: Boolean = false
) {
    OutlinedTextField(
        value = value,
        readOnly = readOnly,
        onValueChange = {},
        placeholder = { Text(placeHolder) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onUploadClick() }, // TextField dapat diklik
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_image), // Ganti dengan ikon sesuai kebutuhan
                contentDescription = "Unggah Foto",
                tint = DisabledColor,
                modifier = Modifier.clickable { onUploadClick() } // Ikon dapat diklik
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}


@Preview(showBackground = true)
@Composable
fun InputFotoFieldPreview(){
    GriyaBugarTheme {
        InputFotoField(
            onUploadClick = {},
            value = "",
            placeHolder = "Masukkan Foto"
        )
    }
}