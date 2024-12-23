package com.griya.griyabugar.ui.components.Button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.DisabledColor
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GreenColor3
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun ButtonBorderWithTextGradient(
    text:String,
    fontSize: TextUnit = 16.sp,
    height: Dp = 55.dp,
    rounded: Dp = 10.dp,
    isDisabled: Boolean = false,
    onClick: ()-> Unit,
){
    val borderColor = if (isDisabled) DisabledColor else GreenColor3
    Button (
        onClick = onClick,
        shape = RoundedCornerShape(rounded),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.White
        ) ,
        border = BorderStroke(3.dp, borderColor),
        enabled = !isDisabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)

    ){
        Text(
            text,
            style = TextStyle(
                fontFamily = poppins,
                textAlign = TextAlign.Center,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                brush = Brush.horizontalGradient(
                    colors = if (isDisabled){
                        listOf(
                            DisabledColor,
                            DisabledColor
                        )
                    } else {
                        listOf(
                            GreenColor1,
                            GreenColor2
                        )
                    }
                )
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ButtonBorderWithTextGradientPreview(){
    GriyaBugarTheme {
        ButtonBorderWithTextGradient(
            text = "Preview Tampilan",
            onClick = {}
        )
    }
}