package com.griya.griyabugar.ui.components.Button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.MainColor

/** Button yang dapat digunakan di screen, set isBorder = true
  jika ingin menggunakan border
**/

@Composable
fun BoxButton(
    text:String,
    color:Color=Color.White,
    fontColor:Color=Color.Black,
    fontSize:TextUnit = 16.sp,
    isBorder:Boolean = false,
    borderColor:Color=Color.Black,
    width:Float = 0.8f,
    height:Float = 0.05f,
    rounded:Float=0.05f,
    modifier: Modifier = Modifier,
    onClick: ()->Unit,
){
 Button (
     onClick = onClick,
     shape = RoundedCornerShape(CornerSize(percent = (rounded * 100).toInt())),
     colors = ButtonDefaults.buttonColors(color),
     border = if (isBorder) BorderStroke(2.dp, borderColor) else null,
     modifier = modifier
         .fillMaxWidth(width)
         .fillMaxHeight(height)

 ){
     Text(
         text,
         color = fontColor,
         textAlign = TextAlign.Center,
         fontSize = fontSize,
         fontWeight = FontWeight.Bold,
         fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
     )
 }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BoxButtonPreview() {
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        BoxButton(
            text = "Bento",
            color = MainColor,
            fontColor = Color.White,
            width = 0.8f,
            height = 0.05f,
            onClick = {}
        )
    }

}