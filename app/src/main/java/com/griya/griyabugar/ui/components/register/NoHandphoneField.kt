package com.griya.griyabugar.ui.components.register

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun NoHandPhoneField(
    onChange: (String) -> Unit,
    value: String
){
    OutlinedTextField(
        value = value,
        onValueChange = {
            onChange(it)
        },
        placeholder = {
            Text(
                text = "81xxxxxxx",
                modifier = Modifier.fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        leadingIcon = {
            Row {
                Text(
                    text = "+62",
                    modifier = Modifier.fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(start = 12.dp)
                )
                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(
                            top = 7.dp,
                            bottom = 7.dp,
                        )
                        .background(color = Color.Gray)
                )
                Spacer(Modifier.width(16.dp))
            }
        },
        textStyle = TextStyle(
            textAlign = TextAlign.Start
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
fun NoHandPhoneFieldPreview(){
    GriyaBugarTheme {
        NoHandPhoneField(
            onChange = {},
            value = ""
        )
    }
}