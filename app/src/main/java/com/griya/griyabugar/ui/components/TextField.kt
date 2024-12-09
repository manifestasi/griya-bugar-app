package com.griya.griyabugar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R

@Composable
fun PasswordTextField(
    state: MutableState<String>,
    passwordVisible: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    fontFamily: FontFamily = FontFamily(Font(R.font.poppins_regular)),
    fontSize: TextUnit = 14.sp,
    hint: String = "Masukan kata sandi",
    hintColor: Color = Color.Gray,
    iconVisible: Int = R.drawable.round_visibility_24,
    iconInvisible: Int = R.drawable.round_visibility_off_24,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    cursorColor: Color = Color.Black,
    cornerRadius: Dp = 10.dp,
    height: Dp = 55.dp,
    padding: Dp = 5.dp,
    onPasswordVisibilityChange: ((Boolean) -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .padding(padding)
    ) {
        BasicTextField(
            value = state.value,
            onValueChange = { state.value = it },
            textStyle = TextStyle(
                fontFamily = fontFamily,
                fontSize = fontSize,
                color = textColor
            ),
            modifier = Modifier
                .padding(start = 5.dp, end = 16.dp)
                .align(Alignment.CenterVertically),
            cursorBrush = SolidColor(cursorColor),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Box {
                    if (state.value.isEmpty()) {
                        Text(
                            text = hint,
                            style = TextStyle(
                                fontFamily = fontFamily,
                                fontSize = fontSize,
                                color = hintColor
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 5.dp)
                        )
                    }
                    innerTextField() // Tampilkan BasicTextField
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                passwordVisible.value = !passwordVisible.value
                onPasswordVisibilityChange?.invoke(passwordVisible.value)
            },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
        ) {
            Icon(
                painter = painterResource(
                    id = if (passwordVisible.value) iconVisible else iconInvisible
                ),
                contentDescription = null,
                tint = textColor
            )
        }
    }
}

@Composable
fun EmailTextField(
    state: MutableState<String>,
    modifier: Modifier = Modifier,
    hint: String = "Masukan Email",
    hintColor: Color = Color.Gray,
    fontFamily: FontFamily = FontFamily(Font(R.font.poppins_regular)),
    fontSize: TextUnit = 14.sp,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    cursorColor: Color = Color.Black,
    cornerRadius: Dp = 10.dp,
    height: Dp = 55.dp,
    padding: Dp = 5.dp,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .padding(padding)
    ) {
        BasicTextField(
            value = state.value,
            onValueChange = { state.value = it },
            textStyle = TextStyle(
                fontFamily = fontFamily,
                fontSize = fontSize,
                color = textColor
            ),
            modifier = Modifier
                .padding(start = 5.dp, end = 16.dp)
                .align(Alignment.CenterVertically),
            cursorBrush = SolidColor(cursorColor),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                Box() {
                    if (state.value.isEmpty()) {
                        Text(
                            text = hint,
                            style = TextStyle(
                                fontFamily = fontFamily,
                                fontSize = fontSize,
                                color = hintColor
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 5.dp)
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}




