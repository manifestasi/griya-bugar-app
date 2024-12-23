package com.griya.griyabugar.ui.components.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins
import com.griya.griyabugar.util.formatTimeInput

@Composable
fun ClockInputDialog(
    title: String,
    onDismiss: () -> Unit,
    btnClickCancel: () -> Unit,
    btnClickAccept: (String) -> Unit
){
    var input by rememberSaveable { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(5.dp),
            color = Color.White,
            modifier = Modifier
                .width(346.dp)
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(Modifier.height(32.dp))

                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight(500),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .width(258.dp),
                    value = input,
                    onValueChange = {
                        input = formatTimeInput(it)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number // Hanya angka
                    ),
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "HH.MM",
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontFamily = poppins,
                                fontSize = 24.sp,
                                color = DisabledColor
                            )
                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        cursorColor = Color.Black
                    )
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .height(44.dp)
                            .width(121.dp),
                        onClick = btnClickCancel,
                        border = BorderStroke(
                            width = 1.dp,
                            color = GreenColor2
                        ),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = GreenColor2,
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = "Batal",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = poppins
                            )
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    Button(
                        onClick = {
                            btnClickAccept(input)
                        },
                        modifier = Modifier
                            .height(44.dp)
                            .width(121.dp)
                            .drawBehind {
                                val shadowColor = Color(0x33000000)
                                val shadowRadius = 5.dp.toPx()
                                val offsetX = (0.3).dp.toPx()
                                val offsetY = (4.6).dp.toPx()
                                drawRoundRect(
                                    color = shadowColor,
                                    topLeft = Offset(x = offsetX, y = offsetY),
                                    size = Size(size.width + 1.dp.toPx(), size.height - 5.dp.toPx()),
                                    cornerRadius = CornerRadius(x = shadowRadius, y = shadowRadius),
                                    style = Fill,
                                    blendMode = BlendMode.SrcOver,
                                )
                            },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                        ),
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            GreenColor1,
                                            GreenColor2
                                        )
                                    )
                                )
                        ) {
                            Text(
                                text = "Simpan",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontFamily = poppins,
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }

                }

                Spacer(Modifier.height(30.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClockInputDialogPreview(){
    GriyaBugarTheme {
        ClockInputDialog(
            title = "Masukkan Jam Datang",
            onDismiss = {},
            btnClickAccept = {},
            btnClickCancel = {}
        )
    }
}