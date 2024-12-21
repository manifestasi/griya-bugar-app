package com.griya.griyabugar.ui.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.GreenColor3
import com.griya.griyabugar.ui.theme.TextColorBlack
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun InputJamDialog(
    input: String = "",
    modifier: Modifier,
    onSave: (String) -> Unit ,
    onCancel: () -> Unit
) {
    var timeValue by remember { mutableStateOf(input) }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(BackgroundColor)
            .padding(20.dp)
            .fillMaxWidth()

    ) {
        Column {
            Text(
                text = "Masukan Jam Datang",
                color = TextColorBlack,
                fontSize = 20.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TimeInputField(
                onChange = { timeValue = it },
                value = timeValue,
                placeholder = "00.00"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween, // Spasi antar tombol
            ) {
                CancelButton(
                    onClick = {
                        onCancel()
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                SaveButton(
                    onClick = { onSave(timeValue) },
                    modifier = Modifier
                        .height(40.dp)
                        .weight(1f)
                )
            }

        }
    }
}


@Composable
fun TimeInputField(
    onChange: (String) -> Unit,
    value: String = "",
    placeholder: String = "00.00"
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            // Filter input hanya angka
            val filteredValue = newValue.text.filter { it.isDigit() }

            // Menambahkan titik setelah 2 angka
            val formatted = when {
                filteredValue.length >= 3 -> {
                    filteredValue.substring(0, 2) + "." + filteredValue.drop(2).take(2)
                }

                else -> filteredValue
            }

            // Memastikan input maksimal 5 karakter (hh.mm)
            val finalValue = formatted.take(5)

            // Update nilai dan posisi kursor
            val cursorPosition = finalValue.length
            textFieldValue = TextFieldValue(finalValue, TextRange(cursorPosition))

            // Mengirim nilai input ke parent
            onChange(finalValue)
        },
        textStyle = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                color = Color.Gray
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TextColorBlack,
            unfocusedBorderColor = TextColorBlack.copy(0.8f),
            cursorColor = TextColorBlack
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    )
}


@Composable
fun CancelButton(
    text: String = "Batal",
    onClick: () -> Unit,
    modifier: Modifier
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = GreenColor3 // Warna hijau teks
        ),
        border = BorderStroke(1.dp, GreenColor3), // Warna border hijau
        shape = RoundedCornerShape(8.dp),
        modifier = modifier// Sudut border membulat
    ) {
        Text(
            text = text,
            fontFamily = poppins,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
fun SaveButton(
    text: String = "Simpan",
    onClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenColor3 // Warna hijau teks
        ),
        elevation = ButtonDefaults.buttonElevation(2.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier// Sudut border membulat
    ) {
        Text(
            text = text,
            fontFamily = poppins,
            fontSize = 16.sp,
            color = TextColorWhite,
            fontWeight = FontWeight.SemiBold
        )
    }
}


